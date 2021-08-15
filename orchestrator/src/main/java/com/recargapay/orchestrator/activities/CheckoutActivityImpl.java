package com.recargapay.orchestrator.activities;

public class CheckoutActivityImpl implements CheckoutActivity{

    @Override
    public void giveProduct() {
        throw new RuntimeException("Exception for SAGA compensation demo");
    }
}
