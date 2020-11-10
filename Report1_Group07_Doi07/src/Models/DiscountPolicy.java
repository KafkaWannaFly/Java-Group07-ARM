package Models;

public class DiscountPolicy {
    public DiscountPolicy() {
    }

    public double PointingPolicy(Bill bill, Customer cus) {
        return 0;
    }

    public double PricingPolicy(Bill bill, Customer cus) {
        return 0;
    }
}

///**
// * Different {@code Customer} has different treatment
// * Depend on their {@code Bill}, this {@code interface} provide different ways to calculate cost
// * @param <B> {@code Bill}
// * @param <C> {@code Customer}
// * @param <R> Return type, should be {@code Double}
// */
//public interface DiscountPolicy<B, C, R> {
//    R PointingPolicy(B bill, C customer);
//    R PricingPolicy(B bill, C customer);
//}
