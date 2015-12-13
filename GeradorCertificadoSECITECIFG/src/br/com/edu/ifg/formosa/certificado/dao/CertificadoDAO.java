package br.com.edu.ifg.formosa.certificado.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import br.com.edu.ifg.formosa.certificado.controle.ConnectionMannager;
import br.com.edu.ifg.formosa.certificado.modelo.AtividadeParticipante;
import br.com.edu.ifg.formosa.certificado.modelo.CertificadoParticipante;
import br.com.edu.ifg.formosa.certificado.util.DataUtil;


public class CertificadoDAO {
	
	public List<CertificadoParticipante> buscaParticipantes() {
		List<CertificadoParticipante> cps = new ArrayList<CertificadoParticipante>();
		Connection con = ConnectionMannager.getConnetion();
		try {
			PreparedStatement stmt = con.prepareStatement("select * from participantes");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				CertificadoParticipante cp = new CertificadoParticipante();
				cp.setNome(rs.getString("nome_partic").toUpperCase());
				cp.setCpf(rs.getString("cpf_partic"));
				
				cps.add(cp);
			} 
			rs.close();
			stmt.close();
			
		}
			
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cps;
	}	
	
	public int buscaCargaHorariaEvento(String cpf){
		int cargaTotal = 0;
		boolean primeiroDia = false;
		boolean segundoDia = false;
		boolean terceiroDia = false;
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo")); 
		//SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd"); 
        String prDia = "16/10/2015";
        String seDia = "17/10/2015";
        String teDia = "18/10/2015";

		try {
			Connection con = ConnectionMannager.getConnetion();

			PreparedStatement stmt = con.prepareStatement("select data from frequencia_evento where cpf_participante='"+cpf+"'");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Date d = rs.getDate("data");
				String dataBD = DataUtil.formatDateShow(d);
				//System.out.println(dataBD + " " + prDia);
				if(dataBD.equals(prDia) && !primeiroDia){
					cargaTotal += 4;
					primeiroDia = true;
				}
				else if(dataBD.equals(seDia) && !segundoDia){
					cargaTotal += 5;
					segundoDia = true;
				}
				else if(dataBD.equals(teDia) && !terceiroDia){
					cargaTotal += 8;
					terceiroDia = true;
				}
				
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	}
		
		return cargaTotal;
	}
	
	public List<AtividadeParticipante> buscaAtividades(String cpf){
		List<AtividadeParticipante> atividades = new ArrayList<AtividadeParticipante>();
		Vector<Integer> vIdAtividades = new Vector<Integer>();
		boolean repetido = false;
		try {
			Connection con = ConnectionMannager.getConnetion();

			PreparedStatement stmt = con.prepareStatement("select id_ativid from frequencia where cpf_partic='"+cpf+"'");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				repetido = false;
				Iterator<Integer> it = vIdAtividades.iterator();
				while(it.hasNext()){
					int x = it.next();
					if(x==rs.getInt("id_ativid"))
						repetido = true;
				}
				if(!repetido){
					AtividadeParticipante a = new AtividadeParticipante();
					a.setNomeAtivid(buscaNomeAtividade(rs.getInt("id_ativid")));
					a.setCargaAtivid(buscaCargaAtividade(rs.getInt("id_ativid")));
					
					//System.out.println(buscaNomeAtividade(rs.getInt("id_ativid")) + " " + DataUtil.formatTimeShow(a.getCargaAtivid()));
					
					atividades.add(a);
					
					
				}
				vIdAtividades.add(rs.getInt("id_ativid"));
				
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	}
		
		return atividades;
	}
	
	private String  buscaNomeAtividade(int id){
		Connection con = ConnectionMannager.getConnetion();
		String nome = "";
		try {
			PreparedStatement stmt = con.prepareStatement("select nome_ativid from atividades where id_ativid = " + id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {			
				nome = rs.getString("nome_ativid");
			} 
			rs.close();
			stmt.close();
			
		}
			
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nome;
		
	}
	
	private Time buscaCargaAtividade(int id){

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo")); 
		Time carga = null;
		boolean first = true;
		PreparedStatement stmt;
		try {
			Connection con = ConnectionMannager.getConnetion();
			stmt = con
					.prepareStatement("select hr_ini, hr_fim from datas d inner join ati_data ad on "
							+ "d.id_data = ad.fk_id_data where ad.fk_id_ativid= "+id+" order by data, hr_ini");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				Time zeroHora = Time.valueOf("00:00:00");
				
				Calendar c1 = Calendar.getInstance();
				c1.setTimeInMillis(DataUtil.formatTimeToSave(rs.getString("hr_ini")).getTime());
				long mili1 = c1.getTimeInMillis();
				
				Calendar c2 = Calendar.getInstance();
				c2.setTimeInMillis(DataUtil.formatTimeToSave(rs.getString("hr_fim")).getTime());
				long mili2 = c2.getTimeInMillis();
				
				long diferenca = mili2 - mili1;
				//System.out.println(diferenca);
				
				Calendar zeroHoraCal = Calendar.getInstance();
				zeroHoraCal.setTime(zeroHora);
				
				Calendar difCal = Calendar.getInstance();
				difCal.setTimeInMillis(diferenca + zeroHoraCal.getTimeInMillis());
				//String diferencaFormatada = new SimpleDateFormat("HH:mm:ss").format(difCal.getTime());
				
				//System.out.println("<br> formatada: " + diferencaFormatada);
				
				if(first){
					carga = new Time(difCal.getTime().getTime());
					first = false;
				}
				else{
					carga = new Time(carga.getTime()+difCal.getTime().getTime()-zeroHoraCal.getTimeInMillis());
				}
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(id==68)
			carga = DataUtil.formatTimeToSave("04:00:00");
		//String diferencaFormatada = new SimpleDateFormat("HH:mm:ss").format(carga);
		//System.out.println(id + " " + diferencaFormatada);
		return carga;
	}

}
