/*
 * Created by IntelliJ IDEA.
 * Language: Java
 * Property of Umesh Gunasekara
 * @Author: SLMORA
 * @DateTime: 7/30/2025 9:55 PM
 */
package com.slmora.common.uuid;

import com.slmora.common.hex.MoraHexUtilities;
import com.slmora.common.logging.MoraLogger;

import java.security.SecureRandom;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * The {@code MoraHexUtilities} Class created for utility class for working with UUIDs.
 * It provides methods to convert, transform, and analyze UUIDs in various formats,
 * including secure generation, string manipulation, and byte array handling.
 * <h4>Key Features</h4>
 * <ul>
 *      <li>Remove or add hyphens to UUID strings</li>
 *      <li>Generate unique UUIDs using SecureRandom</li>
 *      <li>Transform UUIDs to ordered forms and back</li>
 *      <li>Support for byte array conversions using Apache Commons Hex utilities</li>
 *      <li>Logging support using {@link MoraLogger}</li>
 * </ul>
 * <h4>Codes</h4>
 * 1 - {@link }<br>
 * <h4>Methods</h4>
 * <ul>
 *     <li>{@link MoraUuidUtilities#printUUIDDetails(UUID)}</li>
 *     <li>{@link MoraUuidUtilities#removeHyphensFromUUID(UUID)}</li>
 *     <li>{@link MoraUuidUtilities#addingHyphensToUUID(String)}</li>
 *     <li>{@link MoraUuidUtilities#getUniqueStringUUID(boolean)}</li>
 *     <li>{@link MoraUuidUtilities#getOrderedUUIDString(UUID)}</li>
 *     <li>{@link MoraUuidUtilities#getUUIDFromOrderedUUIDString(String)}</li>
 * </ul>
 * <p>
 * <h4>Notes</h4>
 * <ul>
 *     <li>....</li>
 * </ul>
 *
 * @author: SLMORA
 * @since 1.0
 *
 * <h4>Revision History</h4>
 * <blockquote><pre>
 * <br>Version      Date            Editor              Note
 * <br>-------------------------------------------------------
 * <br>1.0          7/30/2025      SLMORA                Initial Code
 * </pre></blockquote>
 *
 */
public class MoraUuidUtilities
{
    private final MoraLogger LOGGER = MoraLogger.getLogger(MoraUuidUtilities.class);

    private volatile SecureRandom numberGenerator = null;
    private final long MSB = 0x8000000000000000L;

    /**
     * Prints detailed properties of the given UUID to the console.
     * Includes variant, version, most/least significant bits, timestamp, clock sequence, and node.
     * <ul>
     *      <li>UUID String</li>
     *      <li>UUID Variant</li>
     *      <li>UUID Version</li>
     *      <li>UUID Most Significant 64 Bits</li>
     *      <li>UUID Last Significant 64 Bits</li>
     *      <li>UUID Time Stamp</li>
     *      <li>UUID Clock Sequence</li>
     *      <li>UUID Node</li>
     * </ul>
     * @param uuid the UUID to analyze
     * @throws UnsupportedOperationException if timestamp-related methods are unsupported
     */
    public void printUUIDDetails(UUID uuid) throws UnsupportedOperationException{
        LOGGER.debug(Thread.currentThread().getStackTrace(), "print UUID details with uuid {} ", (null!=uuid)?uuid:null);
        LOGGER.debug("UUID String : "+uuid.toString());
        LOGGER.debug("UUID Variant : "+uuid.variant());
        LOGGER.debug("UUID Version : "+uuid.version());
        LOGGER.debug("UUID Most Significant 64 Bits : "+uuid.getMostSignificantBits());
        LOGGER.debug("UUID Last Significant 64 Bits : "+uuid.getLeastSignificantBits());
        long uuidTimeStamp =  uuid.timestamp();
        if(uuidTimeStamp != 0L){
            LOGGER.debug("UUID Time Stamp : "+ uuidTimeStamp);
            LOGGER.debug("UUID Clock Sequence : "+uuid.clockSequence());
            LOGGER.debug("UUID Node : "+uuid.node());
        }else{
            LOGGER.debug("UUID Time Stamp : Give UUID is not an time based one, No Time Stamp");
            LOGGER.debug("UUID Clock Sequence : Give UUID is not an time based one, No Clock Sequence");
            LOGGER.debug("UUID Node : Give UUID is not an time based one, No Node");
        }

    }

    /**
     * <p>Will Returns string of given UUID without hyphens</p>
     * <P>Ex:   this will remove all hyphens in uuid as example 123e4567-e89b-12d3-a456-426614174000 will
     *          returns 123e4567e89b12d3a456426614174000</P>
     * @param uuid the UUID to transform
     * @return UUID string without hyphens
     */
    public String removeHyphensFromUUID(UUID uuid){
        String result = uuid.toString().replace("-","");
        LOGGER.debug(Thread.currentThread().getStackTrace(), "Remove hyphens from UUID input {} and get {}", (null!=uuid)?uuid:null, result);
        return result;
    }

    /**
     * <p>Will Returns string of given UUID by adding hyphens</p>
     * <P>Ex:   this will add hyphens in uuid string as example 123e4567e89b12d3a456426614174000 will
     *          returns 123e4567-e89b-12d3-a456-426614174000</P>
     * @param uuidString the UUID string without hyphens
     * @return UUID string with hyphens
     */
    public String addingHyphensToUUID(String uuidString){
        StringBuilder sb =  new StringBuilder();
        sb.append(uuidString.substring(0,8)+"-")
                .append(uuidString.substring(8,12)+"-")
                .append(uuidString.substring(12,16)+"-")
                .append(uuidString.substring(16,20)+"-")
                .append(uuidString.substring(20));
        String result = sb.toString();
        LOGGER.debug(Thread.currentThread().getStackTrace(), "Adding hyphens to UUID input {} and get {}", uuidString, result);
        return result;
    }

    /**
     * <p>Generates a unique UUID string using SecureRandom with optional hyphens.</p>
     * @param withHyphens whether to include hyphens in the result
     * @return randomly generated UUID string
     */
    public String getUniqueStringUUID(boolean withHyphens) {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }
        String uuidWithoutHyphen = Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
        return withHyphens ? addingHyphensToUUID(uuidWithoutHyphen) : uuidWithoutHyphen;
    }

    /**
     * <p>Generate ordered UUID from given UUID</p>
     *
     * @param uuid the UUID to convert
     * @return ordered UUID string
     */
    public String getOrderedUUIDString(UUID uuid) {
        String uuidString = uuid.toString();
        StringBuilder sb = new StringBuilder();
        //can replace append(uuidString.substring(14, 18)) by append(uuidString, 14, 18)
        sb.append(uuidString, 14, 18)
                .append(uuidString, 9, 13)
                .append(uuidString, 0, 8)
                .append(uuidString, 19, 23)
                .append(uuidString.substring(24));
        String result = sb.toString();
        LOGGER.debug(Thread.currentThread().getStackTrace(), "Order UUID String input {} and get {}", uuidString, result);
        return result;
    }

    /**
     * Reconstructs a UUID from an ordered UUID string.
     *
     * @param orderedUUIDString ordered UUID string
     * @return UUID instance
     */
    public UUID getUUIDFromOrderedUUIDString(String orderedUUIDString) {
        StringJoiner joiner = new StringJoiner("-");
        joiner
                .add(orderedUUIDString.substring(8,16))
                .add(orderedUUIDString.substring(4,8))
                .add(orderedUUIDString.substring(0,4))
                .add(orderedUUIDString.substring(16,20))
                .add(orderedUUIDString.substring(20));
        UUID result = UUID.fromString(joiner.toString());
        LOGGER.debug(Thread.currentThread().getStackTrace(), "UUID String input {} and get {}", orderedUUIDString, result);
        return result;
    }

    /**
     * Converts a UUID to a byte array in ordered form using Apache Commons Hex utilities.
     *
     * @param uuid the UUID to convert
     * @return byte array representation
     */
    public byte[] getOrderedUUIDByteArrayFromUUIDWithApacheCommons(UUID uuid) {
        LOGGER.debug(Thread.currentThread().getStackTrace(), "UUID byte from input {}", (null!=uuid)?uuid:null);
        MoraHexUtilities hexUtils = new MoraHexUtilities();
        return hexUtils.convertStringToUnHexByteArrayWithApacheCommons(getOrderedUUIDString(uuid));
    }

    /**
     * Converts an ordered byte array (UUID) to a UUID instance using Apache Commons Hex utilities.
     * 
     * @param orderedUUID byte array containing ordered UUID
     * @return UUID object, or null if input is null
     */
    public UUID getUUIDFromOrderedUUIDByteArrayWithApacheCommons(byte[] orderedUUID) {
        if (orderedUUID == null) {
            LOGGER.debug(Thread.currentThread().getStackTrace(), "UUID from ordered bytes and input orderedUUID is null");
            return null;
        }
        MoraHexUtilities hexUtils = new MoraHexUtilities();
        String orderedUUIDString = hexUtils.convertByteArrayToHexWithApacheCommons(orderedUUID);
        UUID result = getUUIDFromOrderedUUIDString(orderedUUIDString);
        LOGGER.debug(Thread.currentThread().getStackTrace(), "UUID from ordered bytes and get {}", result);
        return result;
    }
}
