// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: columns.proto

package proto;

public interface UserOrBuilder extends
    // @@protoc_insertion_point(interface_extends:proto.User)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int32 user_id = 1;</code>
   */
  int getUserId();

  /**
   * <code>optional string username = 2;</code>
   */
  java.lang.String getUsername();
  /**
   * <code>optional string username = 2;</code>
   */
  com.google.protobuf.ByteString
      getUsernameBytes();

  /**
   * <code>optional string first_name = 3;</code>
   */
  java.lang.String getFirstName();
  /**
   * <code>optional string first_name = 3;</code>
   */
  com.google.protobuf.ByteString
      getFirstNameBytes();

  /**
   * <code>optional string last_name = 4;</code>
   */
  java.lang.String getLastName();
  /**
   * <code>optional string last_name = 4;</code>
   */
  com.google.protobuf.ByteString
      getLastNameBytes();

  /**
   * <code>optional string dob = 5;</code>
   */
  java.lang.String getDob();
  /**
   * <code>optional string dob = 5;</code>
   */
  com.google.protobuf.ByteString
      getDobBytes();

  /**
   * <code>optional int64 create_time_ms = 6;</code>
   */
  long getCreateTimeMs();

  /**
   * <code>optional int64 change_time_ms = 7;</code>
   */
  long getChangeTimeMs();
}