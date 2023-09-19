package io.opentelemetry.javaagent.instrumentation.nohealth;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.sdk.trace.samplers.SamplingDecision;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;
import java.util.List;

public class NoHealthSampler implements Sampler {
    @Override
    public SamplingResult shouldSample(
            Context parentContext,
            String traceId,
            String name,
            SpanKind spanKind,
            Attributes attributes,
            List<LinkData> parentLinks) {
        if (attributes.get(AttributeKey.stringKey("http.target")) != null && attributes.get(AttributeKey.stringKey("http.target")).endsWith("/health")) {
            return SamplingResult.create(SamplingDecision.DROP);
        } else {
            return SamplingResult.create(SamplingDecision.RECORD_AND_SAMPLE);
        }
    }

    @Override
    public String getDescription() {
        return "NoHealthSampler";
    }
}
