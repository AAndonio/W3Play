package utils;

/**
 * Classe contentente costanti rappresentanti i nomi delle colonne delle tabelle utilizzate nel database
 * @author Luca
 */
public class Schema {
	
	/**
	 * Schema per la tabella amministrtore
	 */
	public class Amministratore {
		
		public static final String EMAIL = "Email";
		public static final String RUOLO = "Ruolo";
	}
	
	/**
	 * Schema per la tabella utente
	 */
	public class Utente {
		
		public static final String VIA = "Via";
		public static final String CAP = "Cap";
		public static final String CITTA = "Citta";
		public static final String NOME = "Nome";
		public static final String EMAIL = "Email";
		public static final String CIVICO = "Civico";
		public static final String COGNOME = "Cognome";
		public static final String PASSWORD = "Password";
	}
}
