package pet.perpet.framework.fragment;


import pet.perpet.framework.channel.ChannelStore;

public interface FragmentSupport<P extends UseViewModel> {

    @Deprecated
    P getPresenter();

    P getViewmodel();

    ChannelStore getChannelStore();

    boolean onBackPressed();

    boolean isFragmentActivated();

    void popBackStackImmediate();
}
