package com.example.romain.majeureinfo;

import com.example.romain.majeureinfo.light.LightContextState;

public abstract class RoomContextRule {

        public void apply(LightContextState context) {
            if (condition(context))
                action();
        }

        protected abstract boolean condition(LightContextState context);

        protected abstract void action();

    }
