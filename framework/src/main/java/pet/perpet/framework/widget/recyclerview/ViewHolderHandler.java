package pet.perpet.framework.widget.recyclerview;

final class ViewHolderHandler {

    //======================================================================
    // Variables
    //======================================================================

    private HolderState mHolderState = HolderState.DEFAULT;

    //======================================================================
    // Constructor
    //======================================================================

    public ViewHolderHandler() {
    }

    //======================================================================
    // Public Methods
    //======================================================================

    public boolean isEnable() {
        return mHolderState != HolderState.DEFAULT;
    }

    public boolean isAddViewHolder() {
        return mHolderState.isAddViewHolder();
    }

    public void updateActive() {
        mHolderState = HolderState.ACTIVE;
    }

    public void updateInsert() {
        switch (mHolderState) {
            case ACTIVE:
            case REMOVE:
                update(HolderState.ADD);
                break;
        }
    }

    public void updateRemove() {
        switch (mHolderState) {
            case ACTIVE:
            case ADD:
                update(HolderState.REMOVE);
                break;
        }
    }

    public void update(HolderState holderState) {
        mHolderState = holderState;
    }

    //======================================================================
    // HolderState
    //======================================================================

    public enum HolderState {
        DEFAULT,
        ACTIVE,
        REMOVE,
        ADD;

        public boolean isAddViewHolder() {
            return this == ADD;
        }
    }
}
