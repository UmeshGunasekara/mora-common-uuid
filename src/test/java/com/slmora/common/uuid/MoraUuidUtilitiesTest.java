/*
 * Created by IntelliJ IDEA.
 * Language: Java
 * Property of Umesh Gunasekara
 * @Author: SLMORA
 * @DateTime: 7/31/2025 12:32 AM
 */
package com.slmora.common.uuid;

import com.slmora.common.hex.MoraHexUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code MoraUuidUtilitiesTest} Class created for
 * Codes<br>
 * 1 - {@link }<br>
 * Methods<br>
 * <ul>
 *     <li>{@link }</li>
 * </ul>
 * <p>
 * Notes
 * <ul>
 *     <li>....</li>
 * </ul>
 *
 * @author: SLMORA
 * @since 1.0
 *
 * <blockquote><pre>
 * <br>Version      Date            Editor              Note
 * <br>-------------------------------------------------------
 * <br>1.0          7/31/2025      SLMORA                Initial Code
 * </pre></blockquote>
 */
public class MoraUuidUtilitiesTest
{
    private MoraUuidUtilities uuidUtils;
//    MoraUuidUtilities uuidUtils = new MoraUuidUtilities();

    @BeforeEach
    void setUp() {
        uuidUtils = new MoraUuidUtilities();
    }

//    @Test
//    void testPrintUUIDDetails() {
//        UUID uuid = UUID.randomUUID();
//        assertDoesNotThrow(() -> uuidUtils.printUUIDDetails(uuid));
//    }

    @Test
    void testRemoveHyphensFromUUID() {
        UUID uuid = UUID.randomUUID();
        String result = uuidUtils.removeHyphensFromUUID(uuid);
        assertFalse(result.contains("-"));
        assertEquals(32, result.length());
    }

    @Test
    void testAddingHyphensToUUID() {
        UUID uuid = UUID.randomUUID();
        String noHyphens = uuidUtils.removeHyphensFromUUID(uuid);
        String withHyphens = uuidUtils.addingHyphensToUUID(noHyphens);
        assertEquals(uuid.toString(), withHyphens);
    }

    @Test
    void testGetUniqueStringUUID() {
        String uuid1 = uuidUtils.getUniqueStringUUID(true);
        String uuid2 = uuidUtils.getUniqueStringUUID(false);
        assertEquals(36, uuid1.length());
        assertEquals(32, uuid2.length());
    }

    @Test
    void testGetOrderedUUIDString() {
        UUID uuid = UUID.randomUUID();
        String ordered = uuidUtils.getOrderedUUIDString(uuid);
        assertNotNull(ordered);
        assertEquals(32, ordered.length());
    }

    @Test
    void testGetUUIDFromOrderedUUIDString() {
        UUID original = UUID.randomUUID();
        String ordered = uuidUtils.getOrderedUUIDString(original);
        UUID reconstructed = uuidUtils.getUUIDFromOrderedUUIDString(ordered);
        assertEquals(original, reconstructed);
    }

    @Test
    void testGetOrderedUUIDByteArrayFromUUIDWithApacheCommons() {
        UUID uuid = UUID.randomUUID();
        byte[] byteArray = uuidUtils.getOrderedUUIDByteArrayFromUUIDWithApacheCommons(uuid);
        assertNotNull(byteArray);
        assertTrue(byteArray.length > 0);
    }

    @Test
    void testGetUUIDFromOrderedUUIDByteArrayWithApacheCommons() {
        UUID original = UUID.randomUUID();
        byte[] byteArray = uuidUtils.getOrderedUUIDByteArrayFromUUIDWithApacheCommons(original);
        UUID reconstructed = uuidUtils.getUUIDFromOrderedUUIDByteArrayWithApacheCommons(byteArray);
        assertEquals(original, reconstructed);
    }

    @Test
    void testGetUUIDFromOrderedUUIDByteArrayWithApacheCommons_NullInput() {
        assertNull(uuidUtils.getUUIDFromOrderedUUIDByteArrayWithApacheCommons(null));
    }

}
