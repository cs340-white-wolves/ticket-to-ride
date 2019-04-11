package cs340.TicketToRide.flatfile;

import cs340.TicketToRide.model.db.IDaoFactory;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.db.IUserDao;

public class DaoFactory implements IDaoFactory {
    @Override
    public IUserDao createUserDao() {
        return new UserDao();
    }

    @Override
    public IGameDao createGameDao() {
        return new GameDao();
    }
}
