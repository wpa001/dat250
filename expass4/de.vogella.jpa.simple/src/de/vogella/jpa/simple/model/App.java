package de.vogella.jpa.simple.model;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {

	private static final String PERSISTENCE_UNIT_NAME = "todos";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		after((req, res) -> {
			res.type("application/json");
		});

		get("/todo", (req, res) -> {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			// read the existing entries and return json
			Query q = em.createQuery("SELECT t FROM Todo t");
			List<Todo> todoList = q.getResultList();
			Gson gson = new Gson();
			String jsonInString = gson.toJson(todoList);
			return jsonInString;
		});

		get("/todo/:id", (req, res) -> {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			try {
				// read the existing entry and return json
				Query q = em.createQuery("SELECT t FROM Todo t WHERE t.id = :id");
				q.setParameter("id", Long.parseLong(req.params(":id")));
				Todo todo = (Todo) q.getSingleResult();
				return todo.toJson();
			} catch (NoResultException e) {
				res.status(404);
				return "404: Todo not found";
			}
		});

		put("/todo/:id", (req, res) -> {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			try {
				Gson gson = new Gson();
				Query q = em.createQuery("SELECT t FROM Todo t WHERE t.id = :id");
				q.setParameter("id", Long.parseLong(req.params(":id")));
				Todo orgTodo = (Todo) q.getSingleResult();
				Todo todo = gson.fromJson(req.body(), Todo.class);

				em.getTransaction().begin();
				orgTodo.setSummary(todo.getSummary());
				orgTodo.setDescription(todo.getDescription());
				em.persist(orgTodo);
				em.getTransaction().commit();
				em.close();
				return orgTodo.toJson();
			} catch (NoResultException e) {
				res.status(404);
				return "404: Todo not found";
			}
		});

		delete("/todo/:id", (req, res) -> {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			Query q = em.createQuery("DELETE FROM Todo t WHERE t.id = :id");
			int deleted = q.setParameter("id", Long.parseLong(req.params(":id"))).executeUpdate();
			em.getTransaction().commit();
			em.close();
			if(deleted != 0) {
				return "Todo with id: " + req.params(":id") + " was successfully deleted.";
			} else {
				res.status(404);
				return "404: Todo not found";
			}
		});
		
		post("/todo", (req, res) -> {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			Gson gson = new Gson();
			Todo todo = new Todo();
			Todo newTodo = gson.fromJson(req.body(), Todo.class);
			todo.setSummary(newTodo.getSummary());
			todo.setDescription(newTodo.getDescription());
			em.persist(todo);
	        em.getTransaction().commit();
	        em.close();
			return todo.toJson();
		});
	}

}
