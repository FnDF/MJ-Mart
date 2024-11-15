package edu.mj.mart.utils;

public class SyntheticEnum {
    public enum Role {
        MANAGER(1), STAFF(2);

        public final int value;

        private Role(int value) {
            this.value = value;
        }
    }

    public enum StatusEmployee {
        ACTIVE(1), INACTIVE(0);

        public final int value;

        private StatusEmployee(int value) {
            this.value = value;
        }
    }
}

