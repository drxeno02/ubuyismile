package com.app.amazon.framework.model;

/**
 * Created by leonard on 9/26/2017.
 */

public class ItemId {

    private final String VALUE;
    private final Type TYPE;

    private ItemId(final String value, final Type type) {
        this.VALUE = value;
        this.TYPE = type;
    }

    /**
     * Creates an {@link ItemId} of type ASIN with the given value
     *
     * @param asinValue The ASIN value of the {@link ItemId}
     * @return The created {@link ItemId} of type ASIN
     */
    public static ItemId createAsin(final String asinValue) {
        return create(asinValue, Type.ASIN);
    }

    /**
     * Creates an {@link ItemId} of type EAN with the given value
     *
     * @param eanValue The EAN value of the {@link ItemId}
     * @return The created {@link ItemId} of type EAN
     */
    public static ItemId createEan(final String eanValue) {
        return create(eanValue, Type.EAN);
    }

    /**
     * Creates an {@link ItemId} of type ISBN with the given value
     *
     * @param isbnValue The ISBN value of the {@link ItemId}
     * @return The created {@link ItemId} of type ISBN
     */
    public static ItemId createIsbn(final String isbnValue) {
        return create(isbnValue, Type.ISBN);
    }

    /**
     * Creates an {@link ItemId} of type UPC with the given value
     *
     * @param upcValue The UPC value of the {@link ItemId}
     * @return The created {@link ItemId} of type UPC
     */
    public static ItemId createUpc(final String upcValue) {
        return create(upcValue, Type.UPC);
    }

    /**
     * Creates an {@link ItemId} of given type with the given value
     *
     * @param idValue The value of the {@link ItemId}
     * @param idType  The type of the {@link ItemId}
     * @return The created {@link ItemId} of the given type
     */
    public static ItemId create(final String idValue, final Type idType) {
        return new ItemId(idValue, idType);
    }

    /**
     * Gives the value of this {@link ItemId}
     *
     * @return The value of this {@link ItemId}
     */
    public String getValue() {
        return VALUE;
    }

    /**
     * Gives the type of the {@link ItemId}
     *
     * @return The type of the {@link ItemId}
     */
    public Type getType() {
        return TYPE;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final ItemId id = (ItemId) obj;
        return VALUE.equals(id.VALUE) && TYPE == id.TYPE;
    }

    @Override
    public int hashCode() {
        int result = VALUE.hashCode();
        result = 31 * result + TYPE.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AmazonItemId{" + "value='" + VALUE + '\'' + ", type=" + TYPE + '}';
    }

    /**
     * The {@link ItemId.Type} contains all available IDs managed by Amazon
     */
    public enum Type {

        ASIN("ASIN"),
        ISBN("ISBN"),
        UPC("UPC"),
        EAN("EAN");

        private final String requestValue;

        Type(final String requestValue) {
            this.requestValue = requestValue;
        }

        @Override
        public String toString() {
            return requestValue;
        }
    }

}
