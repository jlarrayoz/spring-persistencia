package uy.org.curso.jpa.main;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import uy.org.curso.jpa.configuration.JpaConfiguration;
import uy.org.curso.jpa.entities.Singer;

@Component
public class JpaMain {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public static void main(String[] args) {
		GenericApplicationContext ctx = new AnnotationConfigApplicationContext(JpaConfiguration.class);
		
		JpaMain app = ctx.getBean(JpaMain.class);
		
		app.run();
		
		ctx.close();
	}
	
	@Transactional
	public void run() {
	
		
		//Listar todos los Singers
		List<Singer> singers = entityManager.createQuery("select s from Singer s", Singer.class).getResultList();
		singers.forEach(System.out::println);
		
		//Buscar un cantante utilizando la NamedQuery que esta credada en la clase Singer
		System.out.println("Buscando Singer con namedQuery");
		TypedQuery<Singer> q = entityManager.createNamedQuery(Singer.FIND_SINGER_BY_ID, Singer.class);
		q.setParameter("id",1L);
		Singer singer = q.getSingleResult();
		System.out.println("Singer:" + singer);
		
		
		entityManager.clear();
		
		System.out.println("Buscando Singer con entityManager");
		Singer singer2 = entityManager.find(Singer.class, 2L);
		System.out.println("Singer2:" + singer2);
		
		singer2.getInstruments().forEach(System.out::println);
		
	}
}
