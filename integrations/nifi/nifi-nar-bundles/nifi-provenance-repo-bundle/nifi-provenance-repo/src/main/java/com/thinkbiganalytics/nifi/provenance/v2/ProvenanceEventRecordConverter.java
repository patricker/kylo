package com.thinkbiganalytics.nifi.provenance.v2;

import com.thinkbiganalytics.nifi.provenance.model.ProvenanceEventAttributeComparator;
import com.thinkbiganalytics.nifi.provenance.model.ProvenanceEventAttributeDTO;
import com.thinkbiganalytics.nifi.provenance.model.ProvenanceEventRecordDTO;

import org.apache.nifi.provenance.ProvenanceEventRecord;
import org.apache.nifi.util.FormatUtils;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by sr186054 on 3/3/16.
 */
public class ProvenanceEventRecordConverter implements Serializable {

    /**
     * Creates a ProvenanceEventDTO for the specified ProvenanceEventRecord.
     *
     * @param event event
     * @return event
     */
    public static ProvenanceEventRecordDTO convert(final ProvenanceEventRecord event) {

        final SortedSet<ProvenanceEventAttributeDTO> attributes = new TreeSet<>(new ProvenanceEventAttributeComparator());

        final Map<String, String> updatedAttrs = event.getUpdatedAttributes();
        final Map<String, String> previousAttrs = event.getPreviousAttributes();

        // add previous attributes that haven't been modified.
        for (final Map.Entry<String, String> entry : previousAttrs.entrySet()) {
            // don't add any attributes that have been updated; we will do that next
            if (updatedAttrs.containsKey(entry.getKey())) {
                continue;
            }

            final ProvenanceEventAttributeDTO attribute = new ProvenanceEventAttributeDTO();
            attribute.setName(entry.getKey());
            attribute.setValue(entry.getValue());
            attribute.setPreviousValue(entry.getValue());
            attributes.add(attribute);
        }

        // Add all of the update attributes
        for (final Map.Entry<String, String> entry : updatedAttrs.entrySet()) {
            final ProvenanceEventAttributeDTO attribute = new ProvenanceEventAttributeDTO();
            attribute.setName(entry.getKey());
            attribute.setValue(entry.getValue());
            attribute.setPreviousValue(previousAttrs.get(entry.getKey()));
            attributes.add(attribute);
        }

        // build the event dto
        final ProvenanceEventRecordDTO dto = new ProvenanceEventRecordDTO();
        dto.setId(String.valueOf(event.getEventId()));
        dto.setAlternateIdentifierUri(event.getAlternateIdentifierUri());
        dto.setAttributes(attributes);
        dto.setTransitUri(event.getTransitUri());
        dto.setEventId(event.getEventId());
        dto.setEventTime(new DateTime(event.getEventTime()));
        dto.setEventDuration(event.getEventDuration());
        dto.setEventType(event.getEventType().name());
        dto.setFileSize(FormatUtils.formatDataSize(event.getFileSize()));
        dto.setFileSizeBytes(event.getFileSize());
        dto.setComponentId(event.getComponentId());
        dto.setComponentType(event.getComponentType());
        dto.setSourceSystemFlowFileId(event.getSourceSystemFlowFileIdentifier());
        dto.setFlowFileUuid(event.getFlowFileUuid());
        dto.setRelationship(event.getRelationship());
        dto.setDetails(event.getDetails());

        // content
        //  dto.setContentEqual(contentAvailability.isContentSame());
        // dto.setInputContentAvailable(contentAvailability.isInputAvailable());
        dto.setInputContentClaimSection(event.getPreviousContentClaimSection());
        dto.setInputContentClaimContainer(event.getPreviousContentClaimContainer());
        dto.setInputContentClaimIdentifier(event.getPreviousContentClaimIdentifier());
        dto.setInputContentClaimOffset(event.getPreviousContentClaimOffset());
        dto.setInputContentClaimFileSizeBytes(event.getPreviousFileSize());
        //  dto.setOutputContentAvailable(contentAvailability.isOutputAvailable());
        dto.setOutputContentClaimSection(event.getContentClaimSection());
        dto.setOutputContentClaimContainer(event.getContentClaimContainer());
        dto.setOutputContentClaimIdentifier(event.getContentClaimIdentifier());
        dto.setOutputContentClaimOffset(event.getContentClaimOffset());
        dto.setOutputContentClaimFileSize(FormatUtils.formatDataSize(event.getFileSize()));
        dto.setOutputContentClaimFileSizeBytes(event.getFileSize());

        // format the previous file sizes if possible
        if (event.getPreviousFileSize() != null) {
            dto.setInputContentClaimFileSize(FormatUtils.formatDataSize(event.getPreviousFileSize()));
        }

        // replay
        //    dto.setReplayAvailable(contentAvailability.isReplayable());
        //   dto.setReplayExplanation(contentAvailability.getReasonNotReplayable());
        dto.setSourceConnectionIdentifier(event.getSourceQueueIdentifier());

        // event duration
        if (event.getEventDuration() >= 0) {
            dto.setEventDuration(event.getEventDuration());
        }

        // lineage duration
        if (event.getLineageStartDate() > 0) {
            final long lineageDuration = event.getEventTime() - event.getLineageStartDate();
            dto.setLineageDuration(lineageDuration);
        }

        // parent uuids
        final List<String> parentUuids = new ArrayList<>(event.getParentUuids());
        Collections.sort(parentUuids, Collator.getInstance(Locale.US));
        dto.setParentUuids(parentUuids);

        // child uuids
        final List<String> childUuids = new ArrayList<>(event.getChildUuids());
        Collections.sort(childUuids, Collator.getInstance(Locale.US));
        dto.setChildUuids(childUuids);

        return dto;


    }
}