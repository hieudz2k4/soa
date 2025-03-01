/**
 * Autogenerated by Thrift Compiler (0.21.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.app.order;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.21.0)", date = "2025-03-01")
public class OrderConfirmation implements org.apache.thrift.TBase<OrderConfirmation, OrderConfirmation._Fields>, java.io.Serializable, Cloneable, Comparable<OrderConfirmation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OrderConfirmation");

  private static final org.apache.thrift.protocol.TField TOTAL_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("totalPrice", org.apache.thrift.protocol.TType.DOUBLE, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new OrderConfirmationStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new OrderConfirmationTupleSchemeFactory();

  public double totalPrice; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_PRICE((short)1, "totalPrice");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TOTAL_PRICE
          return TOTAL_PRICE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    @Override
    public short getThriftFieldId() {
      return _thriftId;
    }

    @Override
    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TOTALPRICE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TOTAL_PRICE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_PRICE, new org.apache.thrift.meta_data.FieldMetaData("totalPrice", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OrderConfirmation.class, metaDataMap);
  }

  public OrderConfirmation() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OrderConfirmation(OrderConfirmation other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalPrice = other.totalPrice;
  }

  @Override
  public OrderConfirmation deepCopy() {
    return new OrderConfirmation(this);
  }

  @Override
  public void clear() {
    setTotalPriceIsSet(false);
    this.totalPrice = 0.0;
  }

  public double getTotalPrice() {
    return this.totalPrice;
  }

  public OrderConfirmation setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
    setTotalPriceIsSet(true);
    return this;
  }

  public void unsetTotalPrice() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTALPRICE_ISSET_ID);
  }

  /** Returns true if field totalPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalPrice() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTALPRICE_ISSET_ID);
  }

  public void setTotalPriceIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTALPRICE_ISSET_ID, value);
  }

  @Override
  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TOTAL_PRICE:
      if (value == null) {
        unsetTotalPrice();
      } else {
        setTotalPrice((java.lang.Double)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_PRICE:
      return getTotalPrice();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_PRICE:
      return isSetTotalPrice();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof OrderConfirmation)
      return this.equals((OrderConfirmation)that);
    return false;
  }

  public boolean equals(OrderConfirmation that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_totalPrice = true && this.isSetTotalPrice();
    boolean that_present_totalPrice = true && that.isSetTotalPrice();
    if (this_present_totalPrice || that_present_totalPrice) {
      if (!(this_present_totalPrice && that_present_totalPrice))
        return false;
      if (this.totalPrice != that.totalPrice)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTotalPrice()) ? 131071 : 524287);
    if (isSetTotalPrice())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(totalPrice);

    return hashCode;
  }

  @Override
  public int compareTo(OrderConfirmation other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetTotalPrice(), other.isSetTotalPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalPrice, other.totalPrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  @Override
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  @Override
  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  @Override
  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("OrderConfirmation(");
    boolean first = true;

    if (isSetTotalPrice()) {
      sb.append("totalPrice:");
      sb.append(this.totalPrice);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OrderConfirmationStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public OrderConfirmationStandardScheme getScheme() {
      return new OrderConfirmationStandardScheme();
    }
  }

  private static class OrderConfirmationStandardScheme extends org.apache.thrift.scheme.StandardScheme<OrderConfirmation> {

    @Override
    public void read(org.apache.thrift.protocol.TProtocol iprot, OrderConfirmation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.totalPrice = iprot.readDouble();
              struct.setTotalPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    @Override
    public void write(org.apache.thrift.protocol.TProtocol oprot, OrderConfirmation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalPrice()) {
        oprot.writeFieldBegin(TOTAL_PRICE_FIELD_DESC);
        oprot.writeDouble(struct.totalPrice);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OrderConfirmationTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    @Override
    public OrderConfirmationTupleScheme getScheme() {
      return new OrderConfirmationTupleScheme();
    }
  }

  private static class OrderConfirmationTupleScheme extends org.apache.thrift.scheme.TupleScheme<OrderConfirmation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OrderConfirmation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTotalPrice()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetTotalPrice()) {
        oprot.writeDouble(struct.totalPrice);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OrderConfirmation struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.totalPrice = iprot.readDouble();
        struct.setTotalPriceIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

