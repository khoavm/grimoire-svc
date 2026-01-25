package com.service.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDUtilTest {

    @Test
    void generateV7_shouldReturnVersion7UUID() {
        // when
        UUID uuid = UUIDUtil.generateV7();

        // then
        assertNotNull(uuid);
        assertEquals(7, uuid.version(), "UUID version should be 7");
    }

    @Test
    void generateV7_shouldReturnIetfVariant() {
        // when
        UUID uuid = UUIDUtil.generateV7();

        // then
        assertEquals(2, uuid.variant(), "UUID variant should be IETF (2)");
    }

    @Test
    void generateV7_shouldGenerateUniqueUUIDs() {
        // given
        int count = 1_000;
        Set<UUID> uuids = new HashSet<>();

        // when
        for (int i = 0; i < count; i++) {
            uuids.add(UUIDUtil.generateV7());
        }

        // then
        assertEquals(count, uuids.size(), "UUIDs should be unique");
    }

    @Test
    void generateV7_shouldBeTimeOrdered() throws InterruptedException {
        // when
        UUID first = UUIDUtil.generateV7();
        Thread.sleep(2); // ensure timestamp difference
        UUID second = UUIDUtil.generateV7();

        // then
        assertTrue(
                first.compareTo(second) < 0,
                "Later UUID should be greater due to timestamp ordering"
        );
    }

    @Test
    void generateV7_shouldContainCurrentTimestampInMostSignificantBits() {
        // given
        long before = System.currentTimeMillis();

        // when
        UUID uuid = UUIDUtil.generateV7();

        long after = System.currentTimeMillis();

        // extract first 48 bits (timestamp)
        long timestamp = (uuid.getMostSignificantBits() >>> 16);

        // then
        assertTrue(
                timestamp >= before && timestamp <= after,
                "UUID timestamp should be within generation time window"
        );
    }
}
