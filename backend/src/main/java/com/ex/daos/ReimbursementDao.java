package com.ex.daos;
import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import com.mongodb.*;
import org.bson.types.ObjectId;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the class that allows for reimbursements to interact with the
 * MongoDB.
 *
 */
public class ReimbursementDao {
    private MongoClient mongoClient;
    private DBCollection reimbursements;
    Logger logger = LogManager.getLogger(ReimbursementDao.class);

    public ReimbursementDao() throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("Project1");
        reimbursements = database.getCollection("reimbursements");
    }

    public ReimbursementDao(String name) throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB(name);
        reimbursements = database.getCollection("reimbursements");
    }

    /**
     * Reads all reimbursements requests that are still pending
     * @param employee
     * @return
     */
    public ArrayList<Reimbursement> getReimbursementsOfEmployee(Employee employee, String pendingStatus) {
        String username = employee.getUsername();
        if(pendingStatus=="pending"){
            DBObject query = new BasicDBObject("username",username).append("approved", "pending");
            DBCursor cursor = reimbursements.find(query);
            ArrayList<Reimbursement> reimbursements = new ArrayList<>();
            while(cursor.hasNext()){
                DBObject reimbursement = cursor.next();
                String user = reimbursement.get("username").toString();
                Double amount = Double.parseDouble(reimbursement.get("amount").toString());
                String date = reimbursement.get("date").toString();
                String id = reimbursement.get("_id").toString();
                String title = reimbursement.get("title").toString();
                String approved = reimbursement.get("approved").toString();
                String approvedBy = "";
                if(approved.equals("true")){
                    approvedBy = reimbursement.get("approvedBy").toString();
                }
                Reimbursement reimbursementPending = new Reimbursement( id, user, title, amount, date, approved, approvedBy);
                reimbursements.add(reimbursementPending);
            }
            return reimbursements;
        }
        else{
            DBObject approvedQuery = new BasicDBObject("username",username).append("approved", "approved");
            DBCursor approvedCursor = reimbursements.find(approvedQuery);
            ArrayList<Reimbursement> returnReimbursements = new ArrayList<>();
            while(approvedCursor.hasNext()){
                DBObject reimbursement = approvedCursor.next();
                String user = reimbursement.get("username").toString();
                Double amount = Double.parseDouble(reimbursement.get("amount").toString());
                String date = reimbursement.get("date").toString();
                String id = reimbursement.get("_id").toString();
                String title = reimbursement.get("title").toString();
                String approved = reimbursement.get("approved").toString();
                String approvedBy = "";
                if(approved.equals("true")){
                    approvedBy = reimbursement.get("approvedBy").toString();
                }
                Reimbursement reimbursementPending = new Reimbursement( id, user, title, amount, date, approved, approvedBy);
                returnReimbursements.add(reimbursementPending);
            }
            DBObject deniedQuery = new BasicDBObject("username",username).append("approved", "denied");
            DBCursor cursor = reimbursements.find(deniedQuery);
            while(cursor.hasNext()){
                DBObject reimbursement = cursor.next();
                String user = reimbursement.get("username").toString();
                Double amount = Double.parseDouble(reimbursement.get("amount").toString());
                String date = reimbursement.get("date").toString();
                String id = reimbursement.get("_id").toString();
                String title = reimbursement.get("title").toString();
                String approved = reimbursement.get("approved").toString();
                String approvedBy = reimbursement.get("approvedBy").toString();
                Reimbursement reimbursementDenied = new Reimbursement( id, user, title, amount, date, approved, approvedBy);
                returnReimbursements.add(reimbursementDenied);
            }
            return returnReimbursements;
        }
    }

    /**
     * This method returns all pending reimbursement requests.
     *
     * @param name by which to query from the database
     * @return an arraylist of reimbursements which satisfy the query
     */
    public ArrayList<Reimbursement> getAllPendingReimbursements(String name) {
        if(name.equals("*")){
            DBObject obj = new BasicDBObject("approved","pending");
            DBCursor cursor = reimbursements.find(obj);
            ArrayList<Reimbursement> reimbursements = new ArrayList<>();
            while(cursor.hasNext()){
                DBObject reimbursement = cursor.next();
                String user = reimbursement.get("username").toString();
                Double amount = Double.parseDouble(reimbursement.get("amount").toString());
                String date = reimbursement.get("date").toString();
                String title = reimbursement.get("title").toString();
                String id = reimbursement.get("_id").toString();
                String approved = reimbursement.get("approved").toString();
                Reimbursement reimbursementPending = new Reimbursement( id, user, title, amount, date, approved);
                reimbursements.add(reimbursementPending);
            }
            return reimbursements;
        }
        else{
            DBObject obj = new BasicDBObject("username", Pattern.compile(name, Pattern.CASE_INSENSITIVE))
                    .append("approved","pending");
            DBCursor cursor = reimbursements.find(obj);
            ArrayList<Reimbursement> reimbursements = new ArrayList<>();
            while(cursor.hasNext()){
                DBObject reimbursement = cursor.next();
                String user = reimbursement.get("username").toString();
                Double amount = Double.parseDouble(reimbursement.get("amount").toString());
                String date = reimbursement.get("date").toString();
                String title = reimbursement.get("title").toString();
                String id = reimbursement.get("_id").toString();
                String approved = reimbursement.get("approved").toString();
                Reimbursement reimbursementPending = new Reimbursement( id, user, title, amount, date, approved);
                reimbursements.add(reimbursementPending);
            }
            return reimbursements;
        }
    }
    /**
     * Writes a new reimbursement request into the database
     * @param reimbursement to add to the database
     * @return true if successful and false otherwise
     */
    public boolean addNewReimbursement(Reimbursement reimbursement) {
        DBObject query = new BasicDBObject("username",reimbursement.getUsername()).
                append("approved",reimbursement.getApproved()).
                append("amount",reimbursement.getAmount()).
                append("date",reimbursement.getDate()).
                append("title",reimbursement.getTitle());
        reimbursements.insert(query);
        logger.info(reimbursement.getUsername()+" submitted a reimbursement for "+ reimbursement.getTitle()+
                " of an amount: "+ reimbursement.getAmount());
        return true;
    }

    /**
     * This method updates the reimbursement request that is provided in the
     * parameter finding it by the object id which is one of the fields of
     * reimbursmenets.
     * @param reimbursement to which the update will be made
     * @return true if      * @return true if successful and false otherwise
     */
    public boolean updateReimbursement(Reimbursement reimbursement) {
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(reimbursement.getId()));
        DBObject object = reimbursements.findAndModify(query, convert(reimbursement));
        return true;
    }

    /**
     * This method turns a reimbursement object into a DBObject ready to be injected
     * into the MongoDB.
     *
     * @param reimbursement the reimbursement to convert
     * @return a DBObject created from the reimbursement
     */
    public DBObject convert(Reimbursement reimbursement){
        return new BasicDBObject("username",reimbursement.getUsername())
                .append("amount", reimbursement.getAmount())
                .append("approved",reimbursement.getApproved())
                .append("title",reimbursement.getTitle())
                .append("date", reimbursement.getDate())
                .append("approvedBy", reimbursement.getApprovedBy());
    }

    /**
     * This method gets all the previous reimbursements that have been either approved
     * or denied.
     *
     * @param name query to search by
     * @return an array list of reimbursements that meets the query's criteria
     */
    public ArrayList<Reimbursement> getAllPrevReimbursements(String name) {
        DBObject query1;
        DBObject query2;
        DBCursor cursor;
        ArrayList<Reimbursement> reimbursementsResult = new ArrayList<>();
        if(name.equals("*")){
            //all reimbursements that are approved
            query1 = new BasicDBObject("approved", "approved");
            query2 = new BasicDBObject("approved", "denied");
            cursor = reimbursements.find(query1);
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String username = obj.get("username").toString();
                String title = obj.get("title").toString();
                String id =  obj.get("_id").toString();
                String date  =  obj.get("date").toString();
                String approved  =  obj.get("approved").toString();
                System.out.println(username+title+id+date);
                String approvedBy ="";
                if(approved.equals(true)){
                      approvedBy=  obj.get("approvedBy").toString();
                }
                Double amount = Double.parseDouble(obj.get("amount").toString());
                Reimbursement reimbursement = new Reimbursement (id, username, title,amount,date, approved, approvedBy);
                reimbursementsResult.add(reimbursement);
            }
            cursor = reimbursements.find(query2);
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String username = obj.get("username").toString();
                String title = obj.get("title").toString();
                String id =  obj.get("_id").toString();
                String date  =  obj.get("date").toString();
                String approved  =  obj.get("approved").toString();
                Double amount = Double.parseDouble(obj.get("amount").toString());
                String approvedBy ="";
                if(approved.equals(true)){
                    approvedBy=  obj.get("approvedBy").toString();
                }
                Reimbursement reimbursement = new Reimbursement( id, username, title, amount, date, approved, approvedBy);
                reimbursementsResult.add(reimbursement);
            }
        }
        else{
            //all reimbursements that are approved
            query1 = new BasicDBObject("username",Pattern.compile(name, Pattern.CASE_INSENSITIVE))
                    .append("approved", "denied");
            query2 = new BasicDBObject("username",Pattern.compile(name, Pattern.CASE_INSENSITIVE))
                    .append("approved", "approved");
            cursor = reimbursements.find(query1);
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String username = obj.get("username").toString();
                String title = obj.get("title").toString();
                String id =  obj.get("_id").toString();
                String date  =  obj.get("date").toString();
                String approved  =  obj.get("approved").toString();
                Double amount = Double.parseDouble(obj.get("amount").toString());
                String approvedBy = obj.get("approvedBy").toString();
                Reimbursement reimbursement = new Reimbursement( id, username, title, amount, date, approved, approvedBy);
                reimbursementsResult.add(reimbursement);
            }
            cursor = reimbursements.find(query2);
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String username = obj.get("username").toString();
                String title = obj.get("title").toString();
                String id =  obj.get("_id").toString();
                String date  =  obj.get("date").toString();
                String approved  =  obj.get("approved").toString();
                Double amount = Double.parseDouble(obj.get("amount").toString());
                String approvedBy = obj.get("approvedBy").toString();
                Reimbursement reimbursement = new Reimbursement( id, username, title, amount, date, approved, approvedBy);
                reimbursementsResult.add(reimbursement);
            }
        }
        return reimbursementsResult;
    }
}