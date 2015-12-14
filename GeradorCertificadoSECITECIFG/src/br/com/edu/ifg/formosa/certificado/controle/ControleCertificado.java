package br.com.edu.ifg.formosa.certificado.controle;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.edu.ifg.formosa.certificado.dao.CertificadoDAO;
import br.com.edu.ifg.formosa.certificado.modelo.AtividadeJasper;
import br.com.edu.ifg.formosa.certificado.modelo.AtividadeParticipante;
import br.com.edu.ifg.formosa.certificado.modelo.CertificadoJasper;
import br.com.edu.ifg.formosa.certificado.modelo.CertificadoParticipante;
import br.com.edu.ifg.formosa.certificado.util.DataUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ControleCertificado {
	
	private CertificadoDAO cDAO = new CertificadoDAO();
	
	public ControleCertificado(){
		
		gerarCertificados();
		
		
	}
	
	public void gerarCertificados(){
		
		List<CertificadoParticipante> cps = cDAO.buscaParticipantes();
		Collections.sort(cps);
		List<CertificadoJasper> lcj = atribuirCHeAtividade(cps);
		
		//Map parametros = new HashMap();
	    //parametros.put("localizacaoPedidosSubreport", "src/pedidos/controle/relatorios/pedidos_subreport.jasper");
	 
	    JRDataSource jrds = new JRBeanCollectionDataSource(lcj);
	    JasperPrint pPrint;
        try {
        	pPrint = JasperFillManager.fillReport("certificadoAtividade01.jasper", null, jrds);
			JasperExportManager.exportReportToPdfFile(pPrint, "certificados.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JOptionPane.showMessageDialog(null, "Relatório gerado. Atualize o projeto, o relatório aparecerá na raiz.", "Info", JOptionPane.INFORMATION_MESSAGE);
		//List<CertificadoJasper> lcj = atribuirCHeAtividade(cps);
		Iterator<CertificadoJasper> it = lcj.iterator();
		int x = 0;
		while(it.hasNext()){
			CertificadoJasper cp = it.next();
			System.out.println(cp.getNome());
			x++;
		}
		System.out.println(x);
		
	}
	
	public List<CertificadoJasper> atribuirCHeAtividade(List<CertificadoParticipante> cps){
		long maiorCarga = 0;
		List<CertificadoParticipante> lcp = new ArrayList<CertificadoParticipante>();
		
		Iterator<CertificadoParticipante> it = cps.iterator();
		int x = 0;
		while(it.hasNext()){
			CertificadoParticipante cp = it.next();
			cp.setCargaTotal(cDAO.buscaCargaHorariaEvento(cp.getCpf()));
			cp.setAtividades(cDAO.buscaAtividades(cp.getCpf()));
			//System.out.println(cp.getCpf() + ": " + cp.getCargaTotal());
			lcp.add(cp);
			x++;
		}
		//System.out.println(x);
		Time zeroHora = Time.valueOf("00:00:00");
		Calendar zeroHoraCal = Calendar.getInstance();
		zeroHoraCal.setTime(zeroHora);
		
		//converter para o formato do Jasper
		List<CertificadoJasper> lcj = new ArrayList<CertificadoJasper>();
		Iterator<CertificadoParticipante> iti = lcp.iterator();
		while(iti.hasNext()){
			CertificadoParticipante cp = iti.next();
			
			//Lista Atividade Jasper
			List<AtividadeJasper> laj = new ArrayList<AtividadeJasper>();
			
			//adiciona atividade padrão para todos participantes com suas devidas cargas
			AtividadeJasper aj = new AtividadeJasper();
			aj.setNomeAtivid("Palestras e Mesas-redonda");
			aj.setCargaAtivid(cp.getCargaTotal()+":00h");
			laj.add(aj);
			
			//Contador da carga horaria total de todas as atividades
			Time cargaTotal = new Time(DataUtil.formatTimeToSave(cp.getCargaTotal()+":00:00").getTime());
			
			//Certificado de cada participante
			CertificadoJasper cj = new CertificadoJasper();
			cj.setNome(cp.getNome());

			Iterator<AtividadeParticipante> iap = cp.getAtividades().iterator();
			
			while(iap.hasNext()){
				
				AtividadeParticipante ap = iap.next();
				AtividadeJasper aja = new AtividadeJasper();
				
				aja.setNomeAtivid(ap.getNomeAtivid());
				aja.setCargaAtivid(DataUtil.formatTimeShow(ap.getCargaAtivid())+"h");
				//System.out.println(cargaTotal.getTime() + ap.getCargaAtivid().getTime() - zeroHoraCal.getTimeInMillis());
				cargaTotal = new Time(cargaTotal.getTime() + ap.getCargaAtivid().getTime() - zeroHoraCal.getTimeInMillis());
			
				laj.add(aja);
			}
			cj.setAtividades(laj);
			
			//Formatando carga horaria total:
			long t = (cargaTotal.getTime()- zeroHoraCal.getTimeInMillis())/1000/60/60;
			if(maiorCarga < t)
				maiorCarga = t;
			
			String horas = t + ":" + DataUtil.formatTimeShowMin(cargaTotal) +"h";
			System.out.println(maiorCarga + ":" + DataUtil.formatTimeShowMin(cargaTotal) +"h");
			
			cj.setCargaTotal(horas);
			if(t!=0)
				lcj.add(cj);
	
		}
		
		return lcj;
		
	}	
	

}
