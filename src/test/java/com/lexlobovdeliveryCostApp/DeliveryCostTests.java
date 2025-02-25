package com.lexlobovdeliveryCostApp;

import com.lexlobov.deliveryCostApp.DeliveryCalculator;
import com.lexlobov.deliveryCostApp.DeliveryWorkLoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.lexlobov.deliveryCostApp.DeliveryWorkLoad.HIGH;
import static com.lexlobov.deliveryCostApp.DeliveryWorkLoad.INTENSIVE;
import static com.lexlobov.deliveryCostApp.DeliveryWorkLoad.NORMAL;
import static com.lexlobov.deliveryCostApp.DeliveryWorkLoad.VERY_INTENSIVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeliveryCostTests {

    private DeliveryCalculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new DeliveryCalculator();
    }

    @Test
    @DisplayName("??????????? ???????? ??????? ?????????? ? ??????, ????? ?????????? ????? 30 ?? ? ???? ???????? ???????")
    void shouldThrowExceptionForFragileItemWithLongDistanceTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateDeliveryCost(31, false, true, NORMAL));
        assertEquals("?????????? ???????????? ???????? ??????? ????? ?? ????????? ????? 30??", exception.getMessage());
    }

    @ParameterizedTest(name = "??????????? ???????? ??????? ?????????? ? ??????, ????? ?????????? ?????? ???? ????? 0")
    @ValueSource(ints = {0, -1})
    void shouldThrowExceptionForDistanceLowerThanZeroTest(int distance) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateDeliveryCost(distance, false, true, NORMAL));
        assertEquals("????????? ???????? ?????? ???? ?????? 0", exception.getMessage());
    }

    @ParameterizedTest(name = "??????????? ???????? ?? ??????????? ?????????? ? ??????, ????? ????????? {0} ? ????????? {1}")
    @MethodSource("provideValidFragileDeliveryScenarios")
    void shouldNotThrowExceptionForFragileItemWithinDistanceLimitTest(int distance, boolean isFragile) {
        assertDoesNotThrow(() -> calculator.calculateDeliveryCost(distance, false, isFragile, NORMAL));
    }

    @ParameterizedTest(name = "??????????? ???????? ??? ???????? ? ??????????? ?? ????????? = {0} ?????????? ?????????? ????????? {3}")
    @MethodSource("provideDistanceCostScenarios")
    void distanceCostTest(int distance, boolean isLarge, boolean isFragile, int expectedCost) {
        assertEquals(expectedCost, calculator.calculateDeliveryCost(distance, isLarge, isFragile, VERY_INTENSIVE));
    }

    @ParameterizedTest(name = "??????????? ???????? ??? ???????? ? ??????????? ?? ??????? = {0} ?????????? ?????????? ???????? {1}")
    @MethodSource("provideSizeComponentScenarios")
    void sizeComponentTest(boolean isLarge, int expectedCost) {
        assertEquals(expectedCost, calculator.calculateDeliveryCost(35, isLarge, false, VERY_INTENSIVE));
    }

    @ParameterizedTest(name = "??????????? ???????? ??? ???????? ? ??????????? ?? ????????? = {0} ?????????? ?????????? ???????? {1}")
    @MethodSource("provideFragilityComponentScenarios")
    void fragilityComponentTest(boolean isFragile, int expectedCost) {
        assertEquals(expectedCost, calculator.calculateDeliveryCost(25, true, isFragile, VERY_INTENSIVE));
    }

    @ParameterizedTest(name = "??????????? ???????? ??? ???????? ? ??????????? ?? ???????? {0} ?????????? ?????????? ???????? {1}")
    @MethodSource("provideLoadQuotientScenarios")
    void loadQuotientApplicationTest(DeliveryWorkLoad load, int expectedCost) {
        assertEquals(expectedCost, calculator.calculateDeliveryCost(5, true, true, load));
    }

    @Test
    @DisplayName("??????????? ???????? ??? ????????? ????????? ????? 400 ?????????? ????????? 400")
    void minimumDeliveryCostTest() {
        assertEquals(400, calculator.calculateDeliveryCost(1, false, false, NORMAL));
    }

    private static Stream<Arguments> provideValidFragileDeliveryScenarios() {
        return Stream.of(
                Arguments.of(30, true),
                Arguments.of(10, true),
                Arguments.of(31, false)
        );
    }

    private static Stream<Arguments> provideDistanceCostScenarios() {
        return Stream.of(
                Arguments.of(1, true, true, 880),
                Arguments.of(2, true, true, 880),
                Arguments.of(3, true, true, 960),
                Arguments.of(9, true, true, 960),
                Arguments.of(10, true, true, 960),
                Arguments.of(11, true, true, 1120),
                Arguments.of(29, true, true, 1120),
                Arguments.of(30, true, true, 1120),
                Arguments.of(31, true, false, 800)
        );
    }

    private static Stream<Arguments> provideSizeComponentScenarios() {
        return Stream.of(
                Arguments.of(true, 800),
                Arguments.of(false, 640)
        );
    }

    private static Stream<Arguments> provideFragilityComponentScenarios() {
        return Stream.of(
                Arguments.of(true, 1120),
                Arguments.of(false, 640)
        );
    }

    private static Stream<Arguments> provideLoadQuotientScenarios() {
        return Stream.of(
                Arguments.of(NORMAL, 600),
                Arguments.of(HIGH, 720),
                Arguments.of(INTENSIVE, 840),
                Arguments.of(VERY_INTENSIVE, 960)
        );
    }
}
