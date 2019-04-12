package cs340.TicketToRide.relational;

import cs340.TicketToRide.model.db.IDaoFactory;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.db.IUserDao;

public class RDDaoFactory implements IDaoFactory {
    @Override
    public IUserDao createUserDao() {
        return new UserDao();
    }

    @Override
    public IGameDao createGameDao() {
        return new GameDao();
    }
}