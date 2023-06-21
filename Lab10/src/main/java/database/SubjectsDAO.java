package database;

import Models.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectsDAO {
    private final Connection _connection;

    public SubjectsDAO() throws Exception {
        _connection = new DbConnection().Connect();
    }

    public boolean Create(Subject subject) {
        String query = " INSERT INTO subjects(name, teacher, faculty) VALUES (?, ?, ?);";

        try {
            PreparedStatement statement = _connection.prepareStatement(query);

            statement.setString(1, subject.getName());
            statement.setString(2, subject.getTeacher());
            statement.setString(3, subject.getFaculty());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException exception) {
            return false;
        }

        return true;
    }

    public boolean Remove(int id) {
        String query = "DELETE FROM subjects WHERE id = (?);";

        try {
            PreparedStatement statement = _connection.prepareStatement(query);

            statement.setInt(1, id);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException exception) {
            return false;
        }
        return true;
    }

    public boolean Update(String id, Subject subject) {
        String query = "UPDATE subjects SET name = (?), teacher = (?), faculty = (?) WHERE id = (?);";

        try {
            PreparedStatement statement = _connection.prepareStatement(query);

            statement.setString(1, id);
            statement.setString(2, subject.getName());
            statement.setString(3, subject.getTeacher());
            statement.setString(4, subject.getFaculty());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException exception) {
            return false;
        }
        return false;
    }

    public ArrayList<Subject> GetAll() {
        String query = "SELECT * FROM subjects;";
        ArrayList<Subject> result = new ArrayList<>();

        try {
            PreparedStatement statement = _connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt(4));
                subject.setName(resultSet.getString(1));
                subject.setTeacher(resultSet.getString(2));
                subject.setFaculty(resultSet.getString(3));

                result.add(subject);
            }
        } catch (SQLException exception) {
        }

        return result;
    }

}
