package br.com.hotel.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.hotel.dao.DAO;
import br.com.hotel.dao.JPAUtil;
import br.com.hotel.modelo.Categoria;
import br.com.hotel.modelo.Quarto;

@ManagedBean
public class QuartoBean {

	private Quarto quarto = new Quarto();
	private Integer categoriaId;

	public Quarto getQuarto() {
		return this.quarto;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	public List<Quarto> getQuartos() {
		return new DAO<Quarto>(Quarto.class).listaTodos();
	}

	public String gravar() {
		Categoria cat = new DAO<Categoria>(Categoria.class)
				.buscaPorId(this.categoriaId);
		System.out.println("Categoria" + cat.getCategoria());
		this.quarto.setCategoria(cat);

		new DAO<Quarto>(Quarto.class).adiciona(this.quarto);
		this.quarto = new Quarto();
		return "relatorioQuartos";
	}

	public List<Categoria> getListaCategorias() {

		EntityManager em = new JPAUtil().getEntityManager();

		CriteriaQuery<Categoria> query = em.getCriteriaBuilder().createQuery(
				Categoria.class);
		query.select(query.from(Categoria.class));
		List<Categoria> lista = em.createQuery(query).getResultList();
		em.close();

		return lista;

	}

}
