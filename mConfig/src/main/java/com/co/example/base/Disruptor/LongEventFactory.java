package com.co.example.base.Disruptor;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory { 
    @Override 
    public Object newInstance() { 
        return new LongEvent(); 
    } 
}