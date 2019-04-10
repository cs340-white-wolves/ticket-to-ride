package cs340.TicketToRide.model.db;

public interface IDaoFactory {
    IUserDao createUserDao();
    IGameDao createGameDao();
}
