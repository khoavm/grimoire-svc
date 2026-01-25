package com.service.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

public class UUIDUtil {
    private static final SecureRandom random = new SecureRandom();

    public static UUID generateV7() {
        long timestamp = System.currentTimeMillis();

        // 1. Fill 16 bytes with random data
        byte[] value = new byte[16];
        random.nextBytes(value);

        // 2. Wrap in ByteBuffer for easy writing
        ByteBuffer buf = ByteBuffer.wrap(value);

        // 3. Overwrite the first 48 bits (6 bytes) with the timestamp
        //    UUID v7 format: unix_ts_ms (48 bits) | ver (4 bits) | rand_a (12 bits) | ...
        buf.putLong(0, (timestamp << 16) | (buf.getLong(0) & 0xFFFF));

        // 4. Set Version to 7 (0111)
        //    The version sits in the high 4 bits of the 7th byte
        value[6] = (byte) ((value[6] & 0x0F) | 0x70);

        // 5. Set Variant to 2 (10xx) - IETF RFC 4122
        //    The variant sits in the high 2 bits of the 9th byte
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        return new UUID(buf.getLong(0), buf.getLong(8));
    }

}
