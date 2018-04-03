import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vad Nik.
 * @version dated Mar 25, 2018.
 */
public class HibernateProcessing {
    private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static final String DB_NAME = "jdbc:sqlite:notes.db";

    HibernateProcessing() {
        if (!new File("notes.db").exists())
            createTable();
    }

    private static void createTable() {
        Session session = createSession(DRIVER_NAME, DB_NAME, "create");
        session.close();
    }

    static void initTable(String name, String text) {
        Session session = createSession(DRIVER_NAME, DB_NAME, "create");
        session.beginTransaction();

        session.save(new Note(name, text));

        session.getTransaction().commit();
        session.close();
    }

    static void insert(String name, String text) {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");
        session.beginTransaction();

        session.save(new Note(getNames().size()+1, name, text));

        session.getTransaction().commit();
        session.close();
    }

    static void insert(int id, String name, String text) {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");
        session.beginTransaction();

        session.save(new Note(id, name, text));

        session.getTransaction().commit();
        session.close();
    }

    @NotUsed
    static String getNameById(int id) {
        String name = null;
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        Query query = session.createQuery("from " + Note.class.getSimpleName() + " where id=:id")
                .setParameter("id", id);
        List<Note> result = (List<Note>)query.getResultList();
        if (result.size() > 0)
            name = result.get(0).getText();

        session.close();
        return name;
    }

    static String getTextByName(String name) {
        String text = null;
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        Query query = session.createQuery("from " + Note.class.getSimpleName() + " where name=:name")
                .setParameter("name", name);
        List<Note> result = (List<Note>)query.getResultList();
        if (result.size() > 0)
            text = result.get(0).getText();

        session.close();
        return text;
    }

    static void setNameById(int id, String name) {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");
        session.beginTransaction();

        Query query = session.createQuery("update " +
                Note.class.getSimpleName() +
                " set name=:name where id=:id")
                .setParameter("name", name)
                .setParameter("id", id);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    static void setTextByName(String name, String text) {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");
        session.beginTransaction();

        Query query = session.createQuery("update " +
                Note.class.getSimpleName() +
                " set text=:text where name=:name")
                .setParameter("text", text)
                .setParameter("name", name);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    static void delete(String name) {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");
        session.beginTransaction();

        session.delete(new Note(getIdByName(name), name, getTextByName(name)));

        session.getTransaction().commit();
        session.close();

        if (getNames().size() == 0)
            deleteAll();
    }

    static void deleteAll() {
        createTable();
    }

    @NotUsed
    static void resetCounter() {
        List<String> changed = new ArrayList<>();
        int count = 0;
        for (String o : getStringedNotes()) {
            changed.add(o.split("~~")[0].replaceAll(o.split("~~")[0], String.valueOf(count)) + "~~" +
                    o.split("~~")[1] + " " + o.split("~~")[2]);
            count++;
        }

        String names = null;
        String texts = null;
        for (String o : changed) {
            names += o.split("~~")[1];
            texts += o.split("~~")[2];
        }

        //initTable(names.split(" ")[0], texts.split(" ")[0]);
        for (String o : changed)
            insert(o.split("~~")[1], o.split("~~")[2]);
    }

    @NotUsed
    static List<String> getNamesInRange(int idFrom, int idTo) {
        List<String> list = new ArrayList<>();
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        Query query = session.createQuery("from " +
                Note.class.getSimpleName() +
                " where name between :idFrom and :idTo")
                .setParameter("idFrom", idFrom)
                .setParameter("idTo", idTo);
        List<Note> notes = query.getResultList();

        session.close();
        for (Note note : notes)
            list.add(note.getId() + "\t" + note.getName() + "\t" + note.getText());
        return list;
    }

    @NotUsed
    static List<String> getTextsInRange(int idFrom, int idTo) {
        List<String> list = new ArrayList<>();
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        Query query = session.createQuery("from " +
                Note.class.getSimpleName() +
                " where text between :idFrom and :idTo")
                .setParameter("idFrom", idFrom)
                .setParameter("idTo", idTo);
        List<Note> notes = query.getResultList();

        session.close();
        for (Note note : notes)
            list.add(note.getId() + "\t" + note.getName() + "\t" + note.getText());
        return list;
    }

    static List<String> getNames() {
        List<String> list = new ArrayList<>();
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
        criteria.from(Note.class);

        List<Note> notes = session.createQuery(criteria).getResultList();

        session.close();
        for (Note note : notes)
            list.add(note.getId() + "\t" + note.getName());
        return list;
    }

    static List<String> getNamesForList() {
        List<String> changed = new ArrayList<>();
        int count = 0;
        for (String ignored : getNames()) {
            changed.add(getNames().get(count).split("\t")[1]);
            count++;
        }
        return changed;
    }

    static List<Integer> getIds() {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
        criteria.from(Note.class);

        List<Note> notes = session.createQuery(criteria).getResultList();

        session.close();

        List<Integer> list = new ArrayList<>();
        for (Note note : notes)
            list.add(note.getId());
        return list;
    }

    static List<String> getStringedNotes() {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
        criteria.from(Note.class);

        List<Note> notes = session.createQuery(criteria).getResultList();

        session.close();

        List<String> list = new ArrayList<>();
        for (Note note : notes)
            list.add(note.getId() + "~~" + note.getName() + "~~" + note.getText());
        return list;
    }

    @NotUsed
    static ArrayList<Note> getObjectedNotes() {
        Session session = createSession(DRIVER_NAME, DB_NAME, "validate");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
        criteria.from(Note.class);

        ArrayList<Note> notes = (ArrayList<Note>) session.createQuery(criteria).getResultList();
        session.close();
        return notes;
    }

    static int getIdByName(String name) {
        List<String> list = getStringedNotes();
        int id = 0;
        for (String o : list) {
            if (o.split("~~")[1].equals(name))
                id = Integer.parseInt(o.split("~~")[0]);
        }
        return id;
    }

    private static Session createSession(String driverName, String dbName, String mode) {
        Session session;

        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.connection.driver_class", driverName);
        settings.put("hibernate.connection.url", dbName);
        settings.put("hibernate.connection.username", "");
        settings.put("hibernate.connection.password", "");
        settings.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", mode);

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(settings).build();

        session = new MetadataSources(registry).addAnnotatedClass(Note.class).getMetadataBuilder().build()
                .getSessionFactoryBuilder().build().openSession();

        return session;
    }
}