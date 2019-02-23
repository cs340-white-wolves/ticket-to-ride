package a340.tickettoride.observerable;

/**
 * Interface for observing changes made to the model.
 */
public interface ModelObserver {
    /**
     * This method will be called when various events occur on the model.
     * Please see ModelChangeType for the specific list of changes that can
     * occur.
     *
     * @param changeType what kind of event it is
     * @param obj the update object (varying types)
     */
    void onModelEvent(ModelChangeType changeType, Object obj);
}
