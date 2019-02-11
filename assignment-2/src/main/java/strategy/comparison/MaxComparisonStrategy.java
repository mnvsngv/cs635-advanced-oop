package strategy.comparison;

import java.util.function.BiFunction;

public class MaxComparisonStrategy
        implements BiFunction<Double, Double, Boolean> {

    @Override
    public Boolean apply(Double x, Double y) {
        return x >= y;
    }
}
