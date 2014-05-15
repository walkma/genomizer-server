package database;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * PREREQUISITES: The construction parameters must reference a postgresql
 * database with the genomizer database tables preloaded. This is done by
 * running the genomizer_database_tables.sql.
 *
 * DatabaseAccessor manipulates the underlying postgresql database using SQL
 * commands.
 *
 * Developed by the Datastorage group for the Genomizer Project, Software
 * Engineering course at Umeå University 2014.
 *
 * @author dv12rwt, Ruaridh Watt
 * @author dv12kko, Kenny Kunto
 * @author dv12ann, André Niklasson
 * @author dv12can, Carl Alexandersson
 * @author yhi04jeo, Jonas Engbo
 * @author oi11mhn, Mattias Hinnerson
 *
 */
public class DatabaseAccessor {

    public static Integer FREETEXT = 1;
    public static Integer DROPDOWN = 2;

    private Connection conn;
    private PubMedToSQLConverter pm2sql;

    /**
     * Creates a databaseAccessor that opens a connection to a database.
     *
     * @param username
     *            - The username to log in to the database as. Should be
     *            "c5dv151_vt14" as of now.
     * @param password
     *            - The password to log in to the database. Should be "shielohh"
     *            as of now.
     * @param host
     *            - The name of the database management system. Will problebly
     *            always be "postgres" unless the DMS is switched with something
     *            else.
     * @param database
     * @throws SQLException
     * @throws IOException
     */
    public DatabaseAccessor(String username, String password, String host,
            String database) throws SQLException {

        String url = "jdbc:postgresql://" + host + "/" + database;

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        conn = DriverManager.getConnection(url, props);
        pm2sql = new PubMedToSQLConverter();
    }

    /**
     * Closes the connection to the database, releasing all resources it uses.
     *
     * @throws SQLException
     *             if a database access error occurs
     */
    public void close() throws SQLException {
        conn.close();
    }

    /**
     * Public method to check if the instance of the class is connected to a
     * database.
     *
     * @return boolean, true if it is connected, otherwise false.
     */
    public boolean isConnected() {
        return conn != null;
    }

    /**
     * Searches the database for Experiments. The search criteria are specified
     * in a String that has the same format as that used by PubMed:
     *
     * <Value>[<Label>] <AND|OR> <Value>[<Label>] ...
     *
     * Round brackets should be used to disambiguate the logical expression.
     *
     * Example: "(Human[Species] OR Fly[Species]) AND Joe Bloggs[Uploader]"
     *
     * @param pubMedString
     *            The String containing the search criteria in PubMed format.
     * @return A List of experiments containing file that fullfill the criteria
     *         specifies in the pubMedString.
     * @throws IOException
     *             If the pubMedString is not in the right format
     * @throws SQLException
     *             if the query does not succeed
     */
    public List<Experiment> search(String pubMedString) throws IOException,
            SQLException {

        if (pm2sql.hasFileConstraint(pubMedString)) {
            return searchFiles(pubMedString);
        }

        return searchExperiments(pubMedString);
    }

    /**
     * Returns an ArrayList which contains the usernames of all the users in the
     * database in the form of strings.
     *
     * @return an ArrayList of usernames.
     * @throws SQLException
     *             if the query does not succeed
     */
    public List<String> getUsers() throws SQLException {

        ArrayList<String> users = new ArrayList<String>();
        String query = "SELECT Username FROM User_Info";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            users.add(rs.getString("Username"));
        }

        stmt.close();

        return users;
    }

    /**
     * Method to add a new user to the database.
     *
     * @param String
     *            the username
     * @param String
     *            the password
     * @param String
     *            the role given to the user ie. "Admin"
     * @throws SQLException
     */
    public void addUser(String username, String password, String role)
            throws SQLException {

        String userString = "INSERT INTO User_Info "
                + "(Username, Password, Role) VALUES " + "(?, ?, ?)";

        PreparedStatement addUser = conn.prepareStatement(userString);
        addUser.setString(1, username);
        addUser.setString(2, password);
        addUser.setString(3, role);
        addUser.executeUpdate();
        addUser.close();
    }

    /**
     * Deletes a user from the database.
     *
     * @param username
     *            the username of the user to be deleted.
     * @throws SQLException
     *             if the query does not succeed
     */
    public void deleteUser(String username) throws SQLException {

        String statementStr = "DELETE FROM User_Info " + "WHERE (Username = ?)";

        PreparedStatement deleteUser = conn.prepareStatement(statementStr);
        deleteUser.setString(1, username);
        deleteUser.executeUpdate();
        deleteUser.close();
    }

    /**
     * Returns the password for the given user. Used for login.
     *
     * @param user
     *            - the username as string
     * @return String - the password
     * @throws SQLException
     *             if the query does not succeed
     */
    public String getPassword(String user) throws SQLException {

        String query = "SELECT Password FROM User_Info "
                + "WHERE (Username = ?)";

        PreparedStatement getPassword = conn.prepareStatement(query);
        getPassword.setString(1, user);
        ResultSet rs = getPassword.executeQuery();
        String pass = null;

        if (rs.next()) {
            pass = rs.getString("password");
        }

        getPassword.close();

        return pass;
    }

    /**
     * Changes the password for a user.
     *
     * @param username
     *            the user to change the password for.
     * @param newPassword
     *            the new password.
     * @return the number of tuples updated in the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int resetPassword(String username, String newPassword)
            throws SQLException {

        String query = "UPDATE User_Info SET Password = ? "
                + "WHERE (Username = ?)";

        PreparedStatement resetPassword = conn.prepareStatement(query);
        resetPassword.setString(1, newPassword);
        resetPassword.setString(2, username);
        int res = resetPassword.executeUpdate();
        resetPassword.close();

        return res;
    }

    /**
     * Gets the role (permissions) for a user.
     *
     * @param username
     *            the user to get the role for.
     * @return the role as a string.
     * @throws SQLException
     *             if the query does not succeed
     */
    public String getRole(String username) throws SQLException {

        String query = "SELECT Role FROM User_Info " + "WHERE (Username = ?)";

        PreparedStatement getRole = conn.prepareStatement(query);
        getRole.setString(1, username);
        ResultSet rs = getRole.executeQuery();
        String role = null;

        if (rs.next()) {
            role = rs.getString("Role");
        }

        getRole.close();

        return role;
    }

    /**
     * Sets the role (permissions) for the user.
     *
     * @param username
     *            the user to set the role for.
     * @param role
     *            the role to set for the user.
     * @return returns the number of tuples updated in the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int setRole(String username, String role) throws SQLException {

        String query = "UPDATE User_Info SET Role = ? "
                + "WHERE (Username = ?)";

        PreparedStatement setRole = conn.prepareStatement(query);
        setRole.setString(1, role);
        setRole.setString(2, username);

        int res = setRole.executeUpdate();
        setRole.close();

        return res;
    }

    /**
     * Gets an experiment from the database.
     *
     * @param expID
     *            the ID of the experiment.
     * @return an Experiment object.
     * @throws SQLException
     *             if the query does not succeed
     */
    public Experiment getExperiment(String expID) throws SQLException {

        String query = "SELECT ExpID FROM Experiment " + "WHERE ExpID = ?";

        PreparedStatement getExp = conn.prepareStatement(query);
        getExp.setString(1, expID);
        ResultSet rs = getExp.executeQuery();
        Experiment e = null;

        if (rs.next()) {
            e = new Experiment(rs.getString("ExpID"));
            e = fillAnnotations(e);
            e = fillFiles(e);
        }

        getExp.close();

        return e;
    }

    /**
     * Adds an experiment ID to the database.
     *
     * @param expID
     *            the ID for the experiment.
     * @return the number of tuples inserted in the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int addExperiment(String expID) throws SQLException {

        String query = "INSERT INTO Experiment " + "(ExpID) VALUES (?)";
        PreparedStatement addExp = conn.prepareStatement(query);
        addExp.setString(1, expID);

        FilePathGenerator.GenerateExperimentFolders(expID);

        int res = addExp.executeUpdate();
        addExp.close();

        return res;
    }

    /**
     * Deletes an experiment from the database.
     *
     * @param expId
     *            the experiment ID.
     * @return the number of tuples deleted.
     * @throws SQLException
     *             if the query does not succeed. Occurs if Experiment contains
     *             at least one file. (All files relating to an experiment must
     *             be deleted first before an experiment can be deleted from the
     *             database)
     */
    public int deleteExperiment(String expId) throws SQLException {

        String statementStr = "DELETE FROM Experiment " + "WHERE (ExpID = ?)";

        PreparedStatement deleteExperiment = conn
                .prepareStatement(statementStr);
        deleteExperiment.setString(1, expId);

        int res = deleteExperiment.executeUpdate();
        deleteExperiment.close();

        return res;

    }

    /**
     * Checks if a given experiment ID exists in the database.
     *
     * @param expID
     *            the experiment ID to look for.
     * @return true if the experiment exists in the database, else false.
     * @throws SQLException
     *             if the query does not succeed
     */
    public boolean hasExperiment(String expID) throws SQLException {

        String query = "SELECT ExpID FROM Experiment " + "WHERE ExpID = ?";

        PreparedStatement hasExp = conn.prepareStatement(query);
        hasExp.setString(1, expID);
        ResultSet rs = hasExp.executeQuery();

        boolean res = rs.next();
        hasExp.close();

        return res;
    }

    /**
     * Updates a value of a single annotation of a unique experiment
     *
     * @param expID
     *            the name of the experiment to annotate.
     * @param label
     *            the annotation to set.
     * @param value
     *            the value of the annotation.
     * @return the number of tuples updated in the database.
     * @throws SQLException
     *             if the query does not succeed
     * @throws IOException
     *             if the value is invalid for the annotation type.
     */
    public int updateExperiment(String expID, String label, String value)
            throws SQLException, IOException {

        if (!isValidAnnotationValue(label, value)) {
            throw new IOException(value
                    + " is not a valid choice for the annotation type " + label);
        }

        String query = "UPDATE Annotated_With SET Value = ? WHERE (Label = ?) AND (ExpID = ?)";

        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, value);
        ps.setString(2, label);
        ps.setString(3, expID);

        int res = ps.executeUpdate();
        ps.close();
        return res;
    }

    /**
     * Annotates an experiment with the given label and value. Checks so that
     * the value is valid if it is a drop down annotation.
     *
     * @param expID
     *            the name of the experiment to annotate.
     * @param label
     *            the annotation to set.
     * @param value
     *            the value of the annotation.
     * @return the number of tuples updated in the database.
     * @throws SQLException
     *             if the query does not succeed
     * @throws IOException
     *             if the value is invalid for the annotation type.
     */
    public int annotateExperiment(String expID, String label, String value)
            throws SQLException, IOException {

        if (!isValidAnnotationValue(label, value)) {
            throw new IOException(value
                    + " is not a valid choice for the annotation type " + label);
        }

        String query = "INSERT INTO Annotated_With " + "VALUES (?, ?, ?)";
        PreparedStatement tagExp = conn.prepareStatement(query);
        tagExp.setString(1, expID);
        tagExp.setString(2, label);
        tagExp.setString(3, value);

        int res = tagExp.executeUpdate();
        tagExp.close();

        return res;
    }

    /**
     * Deletes one annotation from a specific experiment.
     *
     * @param expID
     *            the experiment to delete the annotation from.
     * @param label
     *            the name of the annotation.
     * @return the number of tuples deleted from the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int removeExperimentAnnotation(String expID, String label)
            throws SQLException {

        String statementStr = "DELETE FROM Annotated_With "
                + "WHERE (ExpID = ? AND Label = ?)";

        PreparedStatement deleteTag = conn.prepareStatement(statementStr);
        deleteTag.setString(1, expID);
        deleteTag.setString(2, label);

        int res = deleteTag.executeUpdate();
        deleteTag.close();

        return res;
    }

    /**
     * Gets all the annotation possibilities from the database.
     *
     * @return a Map with the label string as key and datatype as value.
     *
     *         The possible datatypes are FREETEXT and DROPDOWN.
     * @throws SQLException
     *             if the query does not succeed
     */
    public Map<String, Integer> getAnnotations() throws SQLException {

        HashMap<String, Integer> annotations = new HashMap<String, Integer>();
        String query = "SELECT * FROM Annotation";

        Statement getAnnotations = conn.createStatement();
        ResultSet rs = getAnnotations.executeQuery(query);

        while (rs.next()) {
            if (rs.getString("DataType").equalsIgnoreCase("FreeText")) {
                annotations.put(rs.getString("Label"), Annotation.FREETEXT);
            } else {
                annotations.put(rs.getString("Label"), Annotation.DROPDOWN);
            }
        }

        getAnnotations.close();

        return annotations;
    }

    /**
     * Creates an Annotation object from an annotation label.
     *
     * @param label
     *            the name of the annotation to create the object for.
     * @return the Annotation object. If the label does not exist, then null
     *         will be returned.
     * @throws SQLException
     *             if the query does not succeed.
     */
    public Annotation getAnnotationObject(String label) throws SQLException {

        String query = "SELECT * FROM Annotation "
                + "LEFT JOIN Annotation_Choices "
                + "ON (Annotation.Label = Annotation_Choices.Label) "
                + "WHERE Annotation.Label = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, label);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Annotation(rs);
        } else {
            return null;
        }
    }

    /**
     * Creates a list of Annotation objects from a list of annotation labels.
     *
     * @param labels
     *            the list of labels.
     * @return will return a list with all the annotations with valid labels. If
     *         the list with labels is empty or none of the labels are valid,
     *         then it will return null.
     * @throws SQLException
     *             if the query does not succeed.
     */
    public List<Annotation> getAnnotationObjects(List<String> labels)
            throws SQLException {

        List<Annotation> annotations = null;
        Annotation annotation = null;

        for (String label : labels) {
            annotation = getAnnotationObject(label);
            if (annotation != null) {
                if (annotations == null) {
                    annotations = new ArrayList<Annotation>();
                }
                annotations.add(annotation);
            }
        }

        return annotations;
    }

    /**
     * Finds all annotationLabels that exist in the database, example of labels:
     * sex, tissue, etc...
     *
     * @return ArrayList<String> annotationLabels
     */
    public ArrayList<String> getAllAnnotationLabels() {

        ArrayList<String> allAnnotationlabels = new ArrayList<>();

        String findAllLabelsQuery = "SELECT Label FROM Annotation";
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(findAllLabelsQuery);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                allAnnotationlabels.add(res.getString("Label"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return allAnnotationlabels;
    }

    /**
     * Gets the datatype of a given annotation.
     *
     * @param label
     *            annotation label.
     * @return the annotation's datatype (FREETEXT or DROPDOWN).
     *
     * @throws SQLException
     *             if the query does not succeed
     */
    public Integer getAnnotationType(String label) throws SQLException {

        Map<String, Integer> annotations = getAnnotations();

        return annotations.get(label);
    }

    /**
     * Gets the default value for a annotation if there is one, If not it
     * returns NULL.
     *
     * @param annotationLabel
     *            the name of the annotation to check
     * @return The defult value or NULL.
     * @throws SQLException
     */
    public String getDefaultAnnotationValue(String annotationLabel)
            throws SQLException {

        String query = "SELECT DefaultValue FROM Annotation WHERE Label = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, annotationLabel);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            return rs.getString("DefaultValue");
        }

        return null;
    }

    /**
     * Deletes an annotation from the list of possible annotations.
     *
     * @param label
     *            the label of the annotation to delete.
     * @return the number of tuples deleted in the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int deleteAnnotation(String label) throws SQLException {

        String statementStr = "DELETE FROM Annotation " + "WHERE (Label = ?)";
        PreparedStatement deleteAnnotation = conn
                .prepareStatement(statementStr);
        deleteAnnotation.setString(1, label);

        int res = deleteAnnotation.executeUpdate();
        deleteAnnotation.close();

        return res;
    }

    /**
     * Adds a free text annotation to the list of possible annotations.
     *
     * @param label
     *            the name of the annotation.
     * @param required
     *            if the annotation should be forced or not
     * @param defaultValue
     *            the default value this field should take or null if a default
     *            value is not required
     * @return the number of tuples updated in the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int addFreeTextAnnotation(String label, String defaultValue,
            boolean required) throws SQLException {

        String query = "INSERT INTO Annotation "
                + "VALUES (?, 'FreeText', ?, ?)";

        PreparedStatement addAnnotation = conn.prepareStatement(query);
        addAnnotation.setString(1, label);
        addAnnotation.setString(2, defaultValue);
        addAnnotation.setBoolean(3, required);

        int res = addAnnotation.executeUpdate();
        addAnnotation.close();

        return res;
    }

    /**
     * Checks if a given annotation is required to be filled by the user.
     *
     * @param annotationLabel
     *            the name of the annotation to check
     * @return true if it is required, else false
     * @throws SQLException
     */
    public boolean isAnnotationRequiered(String annotationLabel)
            throws SQLException {

        String query = "SELECT Required FROM Annotation WHERE Label = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, annotationLabel);

        ResultSet rs = ps.executeQuery();
        boolean isRequired = false;

        while (rs.next()) {
            isRequired = rs.getBoolean("Required");
        }

        return isRequired;
    }

    /**
     * Gets all the choices for a drop down annotation. Deprecated, use
     * {@link #getChoices(String) getChoices} instead.
     *
     * @param label
     *            the drop down annotation to get the choice for.
     * @return the choices.
     * @throws SQLException
     *             if the query does not succeed
     */
    @Deprecated
    public ArrayList<String> getDropDownAnnotations(String label)
            throws SQLException {

        String query = "SELECT Value FROM Annotation_Choices "
                + "WHERE (Label = ?)";
        ArrayList<String> dropDownStrings = new ArrayList<String>();

        PreparedStatement getDropDownStrings = conn.prepareStatement(query);
        getDropDownStrings.setString(1, label);

        ResultSet rs = getDropDownStrings.executeQuery();

        while (rs.next()) {
            dropDownStrings.add(rs.getString("Value"));
        }

        getDropDownStrings.close();

        return dropDownStrings;
    }

    /**
     * Adds a drop down annotation to the list of possible annotations.
     *
     * @param label
     *            the name of the annotation.
     * @param choices
     *            the possible values for the annotation.
     * @return the number of tuples inserted into the database.
     * @throws SQLException
     *             if the query does not succeed
     * @throws IOException
     *             if the choices are invalid
     */
    public int addDropDownAnnotation(String label, List<String> choices,
            int defaultValueIndex, boolean required) throws SQLException,
            IOException {

        if (choices.isEmpty()) {
            throw new IOException("Must specify at least one choice");
        }

        if (defaultValueIndex < 0 || defaultValueIndex >= choices.size()) {
            throw new IOException("Invalid default value index");
        }

        int tuplesInserted = 0;

        String annotationQuery = "INSERT INTO Annotation "
                + "VALUES (?, 'DropDown', ?, ?)";

        String choicesQuery = "INSERT INTO Annotation_Choices "
                + "(Label, Value) VALUES (?, ?)";

        PreparedStatement addAnnotation = conn
                .prepareStatement(annotationQuery);

        addAnnotation.setString(1, label);
        addAnnotation.setString(2, choices.get(defaultValueIndex));
        addAnnotation.setBoolean(3, required);
        tuplesInserted += addAnnotation.executeUpdate();
        addAnnotation.close();

        PreparedStatement addChoices = conn.prepareStatement(choicesQuery);
        addChoices.setString(1, label);

        for (String choice : choices) {
            addChoices.setString(2, choice);
            try {
                tuplesInserted += addChoices.executeUpdate();
            } catch (SQLException e) {
                /*
                 * Ignore and try adding next choice. This is probably due to
                 * the list of choices containing a duplicate.
                 */
            }
        }

        addChoices.close();

        return tuplesInserted;

    }

    /**
     * Method to add a value to a existing DropDown annotation.
     *
     * @param label
     *            , the label of the chosen DropDown annotation.
     * @param value
     *            , the value that will be added to the DropDown annotation.
     * @return, Integer, how many rows that were added to the database.
     * @throws SQLException
     *             , if the value already exist or another SQL error.
     * @throws IOException
     *             , if the chosen label does not represent a DropDown
     *             annotation.
     */
    public int addDropDownAnnotationValue(String label, String value)
            throws SQLException, IOException {

        String statementStr = "SELECT * FROM Annotation WHERE "
                + "(label = ? AND datatype = 'DropDown')";

        PreparedStatement checkTag = conn.prepareStatement(statementStr);
        checkTag.setString(1, label);

        ResultSet rs = checkTag.executeQuery();
        boolean res = rs.next();
        checkTag.close();

        if (!res) {
            throw new IOException("The annotation of the chosen label"
                    + " is not of type DropDown");
        } else {
            statementStr = "INSERT INTO Annotation_Choices (label , value) "
                    + "VALUES (?,?)";

            PreparedStatement insertTag = conn.prepareStatement(statementStr);

            insertTag.setString(1, label);
            insertTag.setString(2, value);
            int ress = insertTag.executeUpdate();
            insertTag.close();

            return ress;
        }
    }

    /**
     * Method to remove a given annotation of a dropdown- annotation.
     *
     * @param label
     *            , the label of the chosen annotation
     * @param the
     *            value of the chosen annotation.
     * @return Integer, how many values that were deleted.
     * @throws SQLException
     * @throws IOException
     *             , throws an IOException if the chosen value to be removed is
     *             the active DefaultValue of the chosen label.
     *
     */
    public int removeAnnotationValue(String label, String value)
            throws SQLException, IOException {

        String statementStr = "SELECT * FROM Annotation WHERE "
                + "(label = ? AND defaultvalue = ?)";

        PreparedStatement checkTag = conn.prepareStatement(statementStr);
        checkTag.setString(1, label);
        checkTag.setString(2, value);

        ResultSet rs = checkTag.executeQuery();

        boolean res = rs.next();
        checkTag.close();

        if (res) {
            throw new IOException("The chosen value of the label is a"
                    + " default value. Change the default value of the label"
                    + " and run this method again.");
        } else {
            statementStr = "DELETE FROM Annotation_Choices "
                    + "WHERE (label = ? AND value = ?)";

            PreparedStatement deleteTag = conn.prepareStatement(statementStr);
            deleteTag.setString(1, label);
            deleteTag.setString(2, value);

            int ress = deleteTag.executeUpdate();
            deleteTag.close();

            return ress;
        }
    }

    /**
     * Changes the annotation Label value.
     *
     * @param String
     *            oldLabel
     * @param string
     *            newLabel
     * @return boolean true if changed succeeded, false if it failed.
     */
    public boolean changeAnnotationLabel(String oldLabel, String newLabel) {

        String changeLblQuery = "UPDATE Annotation SET Label = ?"
                + " WHERE (Label =?)";

        PreparedStatement lblExp;

        try {
            lblExp = conn.prepareStatement(changeLblQuery);

            lblExp.setString(1, newLabel);
            lblExp.setString(2, oldLabel);
            lblExp.execute();
            return true;

        } catch (SQLException e) {

            System.out.println("Failed to Create changeLabel query");
            return false;
        }
    }

    /*
     * Changes the value of an annotation corresponding to it's label.
     * Parameters: label of annotation, the old value and the new value to
     * change to. Throws an SQLException if the new value already exists in the
     * choices table (changing all males to female, and female is already in the
     * table)
     *
     * @param String label
     *
     * @param String oldValue
     *
     * @param String newValue
     *
     * @throws SQLException
     */
    public void changeAnnotationValue(String label, String oldValue,
            String newValue) throws SQLException {

        String query = "UPDATE Annotation_Choices " + "SET Value = ? "
                + "WHERE Label = ? and Value = ?";

        String query2 = "UPDATE Annotated_With " + "SET Value = ? "
                + "WHERE Label = ? and Value = ?";

        String query3 = "UPDATE Annotation " + "SET DefaultValue = ? "
                + "WHERE Label = ? and DefaultValue = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add(newValue);
        parameters.add(label);
        parameters.add(oldValue);
        statement = bind(statement, parameters);
        statement.executeUpdate();
        statement.close();

        statement = conn.prepareStatement(query2);
        statement = bind(statement, parameters);
        statement.executeUpdate();
        statement.close();

        statement = conn.prepareStatement(query3);
        statement = bind(statement, parameters);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Gets all the choices for a drop down annotation.
     *
     * @param label
     *            the drop down annotation to get the choice for.
     * @return the choices.
     * @throws SQLException
     *             if the query does not succeed
     */
    public List<String> getChoices(String label) throws SQLException {

        String query = "SELECT Value FROM Annotation_Choices "
                + "WHERE Label = ?";
        List<String> choices = new ArrayList<String>();

        PreparedStatement getChoices = conn.prepareStatement(query);
        getChoices.setString(1, label);

        ResultSet rs = getChoices.executeQuery();

        while (rs.next()) {
            choices.add(rs.getString("Value"));
        }

        getChoices.close();

        return choices;
    }

    /**
     * @param expID
     *            The unique name of the experiment. OBS! If not null, this must
     *            reference an experiment that has been previously added.
     * @param fileType
     *            An Integer identifying the file type eg. FileTuple.RAW
     * @param fileName
     * @param inputFileName
     *            The name of the corresponding input file or null if there is
     *            no corresponding input file
     * @param metaData
     *            The parameters used in file creation or null if not applicable
     * @param author
     * @param uploader
     * @param isPrivate
     * @param genomeRelease
     *            The genome release version identifyer (eg. "hg38") or null if
     *            not applicable. OBS! If not null, this must reference a genome
     *            release that has been previously uploaded.
     * @return The FileTuple inserted in the database or null if no file was
     *         entered into the database.
     * @throws SQLException
     *             If the query could not be executed. (Probably because the
     *             file already exists)
     */
    public FileTuple addNewFile(String expID, int fileType, String fileName,
            String inputFileName, String metaData, String author,
            String uploader, boolean isPrivate, String genomeRelease)
            throws SQLException {

        String path = FilePathGenerator.GenerateFilePath(expID, fileType,
                fileName);

        String inputFilePath = FilePathGenerator.GenerateFilePath(expID,
                fileType, inputFileName);

        String query = "INSERT INTO File "
                + "(Path, FileType, FileName, Date, MetaData, InputFilePath, "
                + "Author, Uploader, IsPrivate, ExpID, GRVersion) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addFile = conn.prepareStatement(query);
        addFile.setString(1, path);

        switch (fileType) {
        case FileTuple.RAW:
            addFile.setString(2, "Raw");
            break;
        case FileTuple.PROFILE:
            addFile.setString(2, "Profile");
            break;
        case FileTuple.REGION:
            addFile.setString(2, "Region");
            break;
        default:
            addFile.setString(2, "Other");
            break;
        }

        addFile.setString(3, fileName);
        addFile.setString(4, metaData);
        addFile.setString(5, inputFilePath);
        addFile.setString(6, author);
        addFile.setString(7, uploader);
        addFile.setBoolean(8, isPrivate);
        addFile.setString(9, expID);
        addFile.setString(10, genomeRelease);

        addFile.executeUpdate();
        addFile.close();

        return getFileTuple(path);
    }

    /**
     * Returns the FileTuple object associated with the given filePath.
     *
     * @param filePath
     * @return The corresponding FileTuple or null if no such file exists
     * @throws SQLException
     *             If the query could not be executed.
     */
    public FileTuple getFileTuple(String filePath) throws SQLException {
        String query = "SELECT * FROM File WHERE Path = ?";
        PreparedStatement getFile = conn.prepareStatement(query);
        getFile.setString(1, filePath);
        ResultSet rs = getFile.executeQuery();
        if (rs.next()) {
            return new FileTuple(rs);
        }
        return null;
    }

    // Too many parameters. Should take a JSONObject or FileTuple
    // instead.
    /**
     * Adds a file to the database. Users should migrate to serverAddFile(...)
     * which returns the FileTuple added to the database.
     *
     * @param fileType
     * @param fileName
     * @param metaData
     * @param author
     * @param uploader
     * @param isPrivate
     * @param expID
     * @param grVersion
     * @return the number if tuples inserted to the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    @Deprecated
    public String addFile(String fileType, String fileName, String metaData,
            String author, String uploader, boolean isPrivate, String expID,
            String grVersion) throws SQLException {

        String path = FilePathGenerator.GenerateFilePath(expID, fileType,
                fileName);

        String query = "INSERT INTO File "
                + "(Path, FileType, FileName, Date, MetaData, InputFilePath, "
                + "Author, Uploader, IsPrivate, ExpID, GRVersion) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, NULL, ?, ?, ?, ?, ?)";
        PreparedStatement addFile = conn.prepareStatement(query);

        addFile.setString(1, path);
        addFile.setString(2, fileType);
        addFile.setString(3, fileName);
        addFile.setString(4, metaData);
        addFile.setString(5, author);
        addFile.setString(6, uploader);
        addFile.setBoolean(7, isPrivate);
        addFile.setString(8, expID);
        addFile.setString(9, grVersion);

        addFile.executeUpdate();
        addFile.close();

        return path;
    }

    /**
     * Adds a file to the database with URL. Use clientAddFile(...)
     *
     * @param fileType
     * @param fileName
     * @param metaData
     * @param author
     * @param uploader
     * @param isPrivate
     * @param expID
     * @param grVersion
     * @return the number if tuples inserted to the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    @Deprecated
    public String addFileURL(String fileType, String fileName, String metaData,
            String author, String uploader, boolean isPrivate, String expID,
            String grVersion) throws SQLException {

        String path = FilePathGenerator.GenerateFilePath(expID, fileType,
                fileName);
        String URL = ServerDependentValues.UploadURL;

        String query = "INSERT INTO File "
                + "(Path, FileType, FileName, Date, MetaData, InputFilePath, "
                + "Author, Uploader, IsPrivate, ExpID, GRVersion) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, NULL, ?, ?, ?, ?, ?)";
        PreparedStatement tagExp = conn.prepareStatement(query);
        tagExp.setString(1, path);
        tagExp.setString(2, fileType);
        tagExp.setString(3, fileName);
        tagExp.setString(4, metaData);
        tagExp.setString(5, author);
        tagExp.setString(6, uploader);
        tagExp.setBoolean(7, isPrivate);
        tagExp.setString(8, expID);
        tagExp.setString(9, grVersion);

        tagExp.executeUpdate();
        tagExp.close();

        return URL + path;
    }

    /**
     * Deletes a file from the database.
     *
     * @param path
     *            the path to the file.
     * @return the number of deleted tuples in the database.
     * @throws SQLException
     *             if the query does not succeed
     */
    public int deleteFile(String path) throws SQLException {

        String statementStr = "DELETE FROM File " + "WHERE (Path = ?)";
        PreparedStatement deleteFile = conn.prepareStatement(statementStr);

        deleteFile.setString(1, path);
        int res = deleteFile.executeUpdate();
        deleteFile.close();

        return res;
    }

    /**
     * Deletes a file from the database using the fileID.
     *
     * @param fileID
     *            the fileID of the file to be deleted.
     * @return 1 if deletion was successful, else 0.
     * @throws SQLException
     */
    public int deleteFile(int fileID) throws SQLException {

        String query = "DELETE FROM File " + "WHERE FileID = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, fileID);

        return stmt.executeUpdate();
    }

    /**
     * Checks if the file with the specified fileID exists in the database.
     *
     * @param fileID
     *            the fileID of the file.
     * @return true if the file exists, else false.
     * @throws SQLException
     */
    public boolean hasFile(int fileID) throws SQLException {

        String query = "SELECT fileID FROM File " + "WHERE fileID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, fileID);

        ResultSet rs = stmt.executeQuery();
        boolean res = rs.next();

        if (rs.next()) {
            res = false;
        }

        stmt.close();

        return res;
    }

    /**
     * Checks if the file path is a valid file path. Not used.
     *
     * @param filePath
     * @return
     * @throws SQLException
     *             if the query does not succeed
     */
    @Deprecated
    public boolean isValidFilePath(String filePath) throws SQLException {

        PreparedStatement pStatement = null;
        String query = "SELECT * FROM File Where (Path = ?)";

        pStatement = conn.prepareStatement(query);
        pStatement.setString(1, filePath);

        ResultSet rs = pStatement.executeQuery();

        boolean res = rs.next();
        pStatement.close();

        return res;
    }

    /**
     * Method to convert from raw data to profile data. Returns a list of
     * filepaths
     *
     * @param fileID
     * @param fileType
     * @param fileName
     * @param metaData
     * @param uploader
     * @param grVersion
     * @param expID
     * @return ArrayList<String>
     * @throws SQLException
     */
    public ArrayList<String> process(String fileID, String fileType,
            String fileName, String metaData, String uploader,
            String grVersion, String expID) throws SQLException {

        ArrayList<String> pathList = new ArrayList<String>();
        String ToPath;

        String SelectQuery = "SELECT Path, Author, IsPrivate FROM File"
                + " WHERE (FileID = ?)";
        PreparedStatement ps = conn.prepareStatement(SelectQuery);
        int fID = Integer.parseInt(fileID);
        ps.setInt(1, fID);

        ResultSet rs = ps.executeQuery();

        String fromPath = null;
        boolean isPrivate = false;
        String author = null;

        if (rs.next()) {
            fromPath = rs.getString("Path");
            author = rs.getString("Author");
            isPrivate = rs.getBoolean("IsPrivate");
        } else {
            throw new SQLException("Not a valid fileID");
        }

        ToPath = addFile(fileType, fileName, metaData, author, uploader,
                isPrivate, expID, grVersion);
        ps.close();

        pathList.add(fromPath);
        pathList.add(ToPath);

        return pathList;
    }

    /**
     * Method for getting all stored versions of genome Releases in the database
     *
     * @return ArrayList<String> genome versions.
     * @throws SQLException
     */
    public ArrayList<String> getStoredGenomeVersions() throws SQLException {

        ArrayList<String> allVersions = new ArrayList<String>();

        String getGenVerQuery = "SELECT * FROM Genome_Release";
        PreparedStatement ps = conn.prepareStatement(getGenVerQuery);

        ResultSet res = ps.executeQuery();

        while (res.next()) {
            allVersions.add(res.getString("Version"));
        }

        return allVersions;
    }

    /**
     * Add one genomerelease to the database.
     *
     * @param String
     *            genomeVersion.
     * @param String
     *            specie.
     * @return String filePath, the path where the genome Version file should be
     *         saved.
     * @throws SQLException
     *             if adding query failed.
     */
    public String addGenomeRelease(String genomeVersion, String specie)
            throws SQLException {

        String filePath = "";

        String path = FilePathGenerator.GeneratePathForGenomeFiles(
                genomeVersion, specie);

        String insertGenRelQuery = "INSERT INTO Genome_Release "
                + "(Version,Species,FilePath) " + "VALUES (?,?,?)";

        PreparedStatement ps = conn.prepareStatement(insertGenRelQuery);
        ps.setString(1, genomeVersion);
        ps.setString(2, specie);
        ps.setString(3, path);

        ps.execute();

        return filePath;
    }

    /**
     * Removes one specific genome version stored in the database.
     *
     * @param version
     *            , the genome version.
     * @param specie
     *            .
     * @return boolean, true if succeded, false if failed.
     */
    public boolean removeGenomeRelease(String genomeVersion, String specie) {

        String removeQuery = "DELETE FROM Genome_Release WHERE "
                + "(Version = ? AND Species = ?)";

        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(removeQuery);

            ps.setString(1, genomeVersion);
            ps.setString(2, specie);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Failed to remove genome release!");
            return false;
        }

        return true;
    }

    public String getChainFile(String fromVersion, String toVersion)
            throws SQLException {

        String query = "SELECT FilePath FROM Chain_File WHERE (FromVersion = ?)"
                + " AND (ToVersion = ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, fromVersion);
        ps.setString(2, toVersion);

        ResultSet rs = ps.executeQuery();
        String res = null;

        if (rs.next()) {

            res = rs.getString("FilePath");
        }

        ps.close();

        return res;
    }

    /**
     * Adds a chain file to database for conversions. Parameters: Oldversion,
     * new version and filename. Returns: file path
     *
     * @param String
     *            fromVersion
     * @param String
     *            toVersion
     * @param String
     *            fileName
     * @return String file path
     * @throws SQLException
     */
    public String addChainFile(String fromVersion, String toVersion,
            String fileName) throws SQLException {

        String species = "";
        String speciesQuery = "SELECT Species From Genome_Release"
                + " WHERE (version = ?)";

        PreparedStatement speciesStat = conn.prepareStatement(speciesQuery);
        speciesStat.setString(1, fromVersion);

        ResultSet rs = speciesStat.executeQuery();

        while (rs.next()) {
            species = rs.getString("Species");
        }

        String filePath = FilePathGenerator.GenerateChainFilePath(species,
                fileName);

        String insertQuery = "INSERT INTO Chain_File "
                + "(FromVersion, ToVersion, FilePath) VALUES (?, ?, ?)";

        PreparedStatement insertStat = conn.prepareStatement(insertQuery);
        insertStat.setString(1, fromVersion);
        insertStat.setString(2, toVersion);
        insertStat.setString(3, filePath);
        insertStat.executeUpdate();
        insertStat.close();

        String URL = ServerDependentValues.UploadURL;

        return URL + filePath;
    }

    /**
     * Deletes a chain_file from the database. You find the unique file by
     * sending in the genome version the file converts from and the genome
     * version the file converts to.
     *
     * @param fromVersion
     *            - genome version the Chain_file converts from
     * @param toVersion
     *            - genome version the Chin_file converts to
     * @return the number of deleted tuples in the database. (Should be one if
     *         success)
     * @throws SQLException
     *             - if the query does not succeed
     */
    public int removeChainFile(String fromVersion, String toVersion)
            throws SQLException {

        String query = "DELETE FROM Chain_File WHERE (FromVersion = ?)"
                + " AND (ToVersion = ?)";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, fromVersion);
        ps.setString(2, toVersion);

        int res = ps.executeUpdate();
        ps.close();

        return res;
    }

    public String getFilePath(String fileID) throws SQLException {

    	String query = "SELECT path FROM file WHERE (fileid = ?)";
    	PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, Integer.parseInt(fileID));

        ResultSet rs = ps.executeQuery();

        String res = null;
        if (rs.next()) {
        	res = rs.getString("path");
        }
        ps.close();

    	return res;
    }

    /**
     * Checks so that the annotation value is valid.
     *
     * @param label
     *            the annotation name.
     * @param value
     *            the value to be evaluated.
     * @return true if the value is valid, else false.
     * @throws SQLException
     *             if the query does not succeed
     */
    private boolean isValidAnnotationValue(String label, String value)
            throws SQLException {

        return getAnnotationType(label) == Annotation.FREETEXT
                || getChoices(label).contains(value);
    }

    /**
     * Adds all the files that belong to the experiment to an Experiment object.
     *
     * @param e
     *            the experiment to add files to.
     * @return the Experiment object containing all its files.
     * @throws SQLException
     *             if the query does not succeed
     */
    private Experiment fillFiles(Experiment e) throws SQLException {

        String query = "SELECT * FROM File " + "WHERE ExpID = ?";
        PreparedStatement getFiles = conn.prepareStatement(query);
        getFiles.setString(1, e.getID());
        ResultSet rs = getFiles.executeQuery();

        while (rs.next()) {
            e.addFile(new FileTuple(rs));
        }

        getFiles.close();

        return e;
    }

    /**
     * Fill an Experiment object with all annotations that exists for that
     * experiment.
     *
     * @param e
     *            the Experiment object.
     * @return the Experiment object containing all it's annotations.
     * @throws SQLException
     *             if the query does not succeed
     */
    private Experiment fillAnnotations(Experiment e) throws SQLException {

        String query = "SELECT Label, Value FROM Annotated_With "
                + "WHERE ExpID = ?";
        PreparedStatement getExpAnnotations = conn.prepareStatement(query);
        getExpAnnotations.setString(1, e.getID());
        ResultSet rs = getExpAnnotations.executeQuery();

        while (rs.next()) {
            e.addAnnotation(rs.getString("Label"), rs.getString("Value"));
        }

        getExpAnnotations.close();

        return e;
    }

    private List<Experiment> searchExperiments(String pubMedString)
            throws IOException, SQLException {

        String query = pm2sql.convertExperimentSearch(pubMedString);
        List<String> params = pm2sql.getParameters();

        PreparedStatement getFiles = conn.prepareStatement(query);
        getFiles = bind(getFiles, params);

        ResultSet rs = getFiles.executeQuery();
        ArrayList<Experiment> experiments = new ArrayList<Experiment>();

        while (rs.next()) {
            Experiment exp = new Experiment(rs.getString("ExpID"));
            exp = fillAnnotations(exp);
            exp = fillFiles(exp);
            experiments.add(exp);
        }

        return experiments;
    }

    private List<Experiment> searchFiles(String pubMedString)
            throws IOException, SQLException {

        String query = pm2sql.convertFileSearch(pubMedString);
        List<String> params = pm2sql.getParameters();

        PreparedStatement getFiles = conn.prepareStatement(query);
        getFiles = bind(getFiles, params);

        ResultSet rs = getFiles.executeQuery();
        ArrayList<Experiment> experiments = new ArrayList<Experiment>();

        if (!rs.next()) {
            return experiments;
        }

        String expId = rs.getString("ExpId");
        Experiment exp = new Experiment(expId);
        exp = fillAnnotations(exp);
        exp.addFile(new FileTuple(rs));

        while (rs.next()) {
            expId = rs.getString("ExpId");

            if (exp.getID().equals(expId)) {
                exp.addFile(new FileTuple(rs));
            } else {
                experiments.add(exp);
                exp = new Experiment(expId);
                exp = fillAnnotations(exp);
                exp.addFile(new FileTuple(rs));
            }
        }

        experiments.add(exp);

        return experiments;
    }

    private PreparedStatement bind(PreparedStatement query, List<String> params)
            throws SQLException {

        for (int i = 0; i < params.size(); i++) {
            query.setString(i + 1, params.get(i));
        }

        return query;
    }
}
