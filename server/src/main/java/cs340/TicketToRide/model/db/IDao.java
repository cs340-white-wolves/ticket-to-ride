package cs340.TicketToRide.model.db;

public interface IDao {
    void beginTransaction();
    void endTransaction();
    void clearAll();
}
