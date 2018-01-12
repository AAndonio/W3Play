package bean;

import java.sql.SQLException;

import model.StatisticheDAO;

/**
 * Statistiche di sistema
 * @author Luca
 *
 */
public class Statistiche {
	
	private int numProdottiCatalogo;
	private int numProdottiVenduti;
	private int numUtentiRegistrati;
	private float guadagnoUltimoMese;
	private int avgOrdiniUtente;
	
	public Statistiche() {
		numProdottiCatalogo = 0;
		numProdottiVenduti = 0;
		numUtentiRegistrati = 0;
		guadagnoUltimoMese = 0f;
		avgOrdiniUtente = 0;
	}
	
	public Statistiche(int numProdottiCatalogo, int numProdottiVenduti, int numUtentiRegistrati,
			float guadagnoUltimoMese, int avgOrdiniUtente) {
		super();
		this.numProdottiCatalogo = numProdottiCatalogo;
		this.numProdottiVenduti = numProdottiVenduti;
		this.numUtentiRegistrati = numUtentiRegistrati;
		this.guadagnoUltimoMese = guadagnoUltimoMese;
		this.avgOrdiniUtente = avgOrdiniUtente;
	}
	
	public void aggiorna() {
		
		try {
			 
			Statistiche s = StatisticheDAO.doCalculate();
			
			this.guadagnoUltimoMese = s.guadagnoUltimoMese;
			this.numProdottiCatalogo = s.numProdottiCatalogo;
			this.numProdottiVenduti = s.numProdottiVenduti;
			this.numUtentiRegistrati = s.numUtentiRegistrati;
			this.avgOrdiniUtente = s.avgOrdiniUtente;
			
		} catch (SQLException e) {
	
			System.err.println("errore nel recupero delle statistiche");
		}
	}
	
	public int getNumeroProdottiCatalogo() {
		return numProdottiCatalogo;
	}

	public void setNumeroProdottiCatalogo(int numProdottiCatalogo) {
		this.numProdottiCatalogo = numProdottiCatalogo;
	}

	public int getNumeroProdottiVenduti() {
		return numProdottiVenduti;
	}

	public void setNumeroProdottiVenduti(int numProdottiVenduti) {
		this.numProdottiVenduti = numProdottiVenduti;
	}

	public int getNumeroUtentiRegistrati() {
		return numUtentiRegistrati;
	}

	public void setNumeroUtentiRegistrati(int numUtentiRegistrati) {
		this.numUtentiRegistrati = numUtentiRegistrati;
	}

	public float getGuadagnoUltimoMese() {
		return guadagnoUltimoMese;
	}

	public void setGuadagnoUltimoMese(float guadagnoUltimoMese) {
		this.guadagnoUltimoMese = guadagnoUltimoMese;
	}

	public int getAverageOrdiniUtente() {
		return avgOrdiniUtente;
	}

	public void setAverageOrdiniUtente(int avgOrdiniUtente) {
		this.avgOrdiniUtente = avgOrdiniUtente;
	}
	
	@Override
	public String toString() {
		
		String format = "[Statistiche: {\n\tprodottiCatalodo: %d,\n\tprodottiVenduti: %d,\n\tutentiRegistrati: %d,\n\tguadagno: %f,\n\tmediaOrdini: %d\n}]";
		
		return String.format(format, numProdottiCatalogo, numProdottiVenduti, numUtentiRegistrati, guadagnoUltimoMese, avgOrdiniUtente);
	}
}
