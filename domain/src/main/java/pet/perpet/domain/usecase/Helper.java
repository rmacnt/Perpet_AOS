package pet.perpet.domain.usecase;


import pet.perpet.data.repository.base.Repository;

final class Helper {

    @SuppressWarnings("unchecked")
    public static void applyParameter(Repository repository, Object parameter) {
        try {
            repository.parameter(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
