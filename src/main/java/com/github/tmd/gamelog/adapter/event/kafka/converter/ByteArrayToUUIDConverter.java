package com.github.tmd.gamelog.adapter.event.kafka.converter;

import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ByteArrayToUUIDConverter implements Converter<byte[], UUID> {

  @Override
  public UUID convert(byte[] source) {
    return UUID.fromString(new String(source));
  }
}
