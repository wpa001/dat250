package de.vogella.jpa.simple.main;

import de.vogella.jpa.simple.banking.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MainBanking {
	private static EntityManagerFactory factory;
    private static final String PERSISTENCE_NAME = "banking";

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        
        Person person = new Person();
        person.setName("Wojtek");
        em.persist(person);
        
        Bank bank = new Bank();
        bank.setName("DnB");
        em.persist(bank);
        
        Pincode pin = new Pincode();
        pin.setPincode(4141);
        em.persist(pin);
        
        CreditCard card = new CreditCard();
        card.setBalance(1337);
        card.setBank(bank);
        card.setPincode(pin);
        em.persist(card);
        List<CreditCard> cards = new ArrayList<>();
        cards.add(card);
        person.setCreditCards(cards);
        
        Address adr = new Address();
        adr.setStreet("Gatensvei");
        adr.setNumber(42);
        em.persist(adr);
        List<Address> adrs = new ArrayList<>();
        adrs.add(adr);
        person.setAddresses(adrs);
        
        em.getTransaction().commit();
        
        // read the existing entries and write to console
        Query q = em.createQuery("SELECT p FROM Person p");
        @SuppressWarnings("unchecked")
		List<Person> personList = q.getResultList();
        for (Person pers: personList) {
            System.out.println(pers);
        }
        System.out.println("Size: " + personList.size());

    }
}
