package pet.perpet.framework.widget.selector;


import pet.perpet.framework.widget.selector.SelectorHolder;

public class SingleSelector extends MultiSelector {

    @Override
    public void setSelected(int position, boolean isSelected) {
        if (isSelected) {
            for (Integer selectedPosition : getSelectedPositions()) {
                if (selectedPosition != position) {
                    super.setSelected(selectedPosition, false);
                }
            }
        }
        super.setSelected(position, isSelected);
    }

    @Override
    public void setSelected(SelectorHolder holder, boolean isSelected) {
        if (isSelected(holder.getId()) == true) {
            return;
        }
        super.setSelected(holder, isSelected);
    }
}
