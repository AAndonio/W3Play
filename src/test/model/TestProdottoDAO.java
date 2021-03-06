package test.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.*;

import bean.Prodotto;
import model.ProdottoDAO;
import utils.DBConnection;

/**
 * CLasse di test per {@link ProdottoDAO}
 * @author Antonio
 */
public class TestProdottoDAO {
	
	private static Prodotto articolo;
	private static String QRY = "SELECT * FROM prodotto ORDER BY idProdotto DESC LIMIT 1";
	private static Connection con = null;
	private static Statement stmt = null;

	@BeforeClass
	public static void setUpBeforeClass() {

		LocalDate date = LocalDate.now();
		articolo = new Prodotto();
		articolo.setNome("product-name");
		articolo.setProduttore("product-producer");
		articolo.setPiattaforma("product-platform");
		articolo.setGenere("product-genere");
		articolo.setDescrizione("product-description");
		articolo.setImmagine("imagePath");
		articolo.setPrezzo(Float.parseFloat("70"));
		articolo.setDisponibilita(Integer.parseInt("0"));
		articolo.setDataUscita(date);
		articolo.setVenduti(0);
		articolo.setLinkVideo("https://www.youtube.com/embed/iaHDwgMSNAM");

	}

	@Test
	public void TestDoSave() throws SQLException {

		ProdottoDAO.doSave(articolo);

		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QRY);
			while (rs.next()) {
				articolo.setIdProdotto(rs.getInt(1));
			}
		} catch (Exception e) {

		}

		Prodotto p = ProdottoDAO.doRetrieveByKey(articolo.getIdProdotto());

		Assert.assertEquals(articolo.getNome(), p.getNome());
		Assert.assertEquals(articolo.getProduttore(), p.getProduttore());
		Assert.assertEquals(articolo.getDescrizione(), p.getDescrizione());
		Assert.assertEquals(articolo.getGenere(), p.getGenere());
		Assert.assertEquals(articolo.getImmagine(), p.getImmagine());
		Assert.assertEquals(articolo.getLinkVideo(), p.getLinkVideo());
		Assert.assertEquals(articolo.getPiattaforma(), p.getPiattaforma());
		// Assert.assertEquals((articolo.getPrezzo(),p.getPrezzo());
		Assert.assertEquals(articolo.getDisponibilita(), p.getDisponibilita());
		Assert.assertEquals(articolo.getIdProdotto(), p.getIdProdotto());
		Assert.assertEquals(articolo.getDataUscita(), p.getDataUscita());

	}

	@Test
	public void TestDoUpdate() throws SQLException {

		articolo.setNome("product-name2");
		articolo.setProduttore("product-producer2");
		articolo.setPiattaforma("product-platform2");
		articolo.setGenere("product-genere2");
		articolo.setDescrizione("product-description2");
		articolo.setImmagine("imagePath2");
		articolo.setPrezzo(Float.parseFloat("72"));
		articolo.setDisponibilita(Integer.parseInt("0"));
		articolo.setVenduti(0);
		articolo.setLinkVideo("https://www.youtube.com/embed/iaHDwgMSNAM");
		ProdottoDAO.doUpdate(articolo);

		Prodotto p = ProdottoDAO.doRetrieveByKey(articolo.getIdProdotto());

		Assert.assertEquals(articolo.getNome(), p.getNome());
		Assert.assertEquals(articolo.getProduttore(), p.getProduttore());
		Assert.assertEquals(articolo.getDescrizione(), p.getDescrizione());
		Assert.assertEquals(articolo.getGenere(), p.getGenere());
		Assert.assertEquals(articolo.getImmagine(), p.getImmagine());
		Assert.assertEquals(articolo.getLinkVideo(), p.getLinkVideo());
		Assert.assertEquals(articolo.getPiattaforma(), p.getPiattaforma());
		// Assert.assertEquals((articolo.getPrezzo(),p.getPrezzo());
		Assert.assertEquals(articolo.getDisponibilita(), p.getDisponibilita());
		Assert.assertEquals(articolo.getIdProdotto(), p.getIdProdotto());
		Assert.assertEquals(articolo.getDataUscita(), p.getDataUscita());

	}

	@Test
	public void TestDoRetrieveByKey() throws SQLException {

		Prodotto p = ProdottoDAO.doRetrieveByKey(articolo.getIdProdotto());
		Assert.assertEquals(articolo.getNome(), p.getNome());
		Assert.assertEquals(articolo.getProduttore(), p.getProduttore());
		Assert.assertEquals(articolo.getDescrizione(), p.getDescrizione());
		Assert.assertEquals(articolo.getGenere(), p.getGenere());
		Assert.assertEquals(articolo.getImmagine(), p.getImmagine());
		Assert.assertEquals(articolo.getLinkVideo(), p.getLinkVideo());
		Assert.assertEquals(articolo.getPiattaforma(), p.getPiattaforma());
		// Assert.assertEquals((articolo.getPrezzo(),p.getPrezzo());
		Assert.assertEquals(articolo.getDisponibilita(), p.getDisponibilita());
		Assert.assertEquals(articolo.getIdProdotto(), p.getIdProdotto());
		Assert.assertEquals(articolo.getDataUscita(), p.getDataUscita());

	}

	@AfterClass
	public static void tearDown() throws Exception {
		
		con = DBConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("delete from prodotto where nome = ?");
		stmt.setString(1, articolo.getNome());
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}
}
