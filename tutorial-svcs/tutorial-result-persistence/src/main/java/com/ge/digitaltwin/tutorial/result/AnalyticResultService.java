package com.ge.digitaltwin.tutorial.result;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

@Service
public class AnalyticResultService {

    static final String ANALYTIC_RESULT_TOPIC = "/topic/analyticResults";

    private final AnalyticResultRepository analyticResultRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnalyticResultService(AnalyticResultRepository analyticResultRepository, SimpMessagingTemplate simpMessagingTemplate,
            @SuppressWarnings("SpringJavaAutowiringInspection") JdbcTemplate jdbcTemplate) {
        this.analyticResultRepository = analyticResultRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveAnalyticResults(List<AnalyticResult> analyticResults) {
        analyticResultRepository.save(analyticResults);
    }

    public void notifyClientsOfNewResults(List<String> assetIds) {
        assetIds.stream().forEach(assetId -> simpMessagingTemplate.convertAndSend(ANALYTIC_RESULT_TOPIC, assetId));
    }

    public void streamResultsForAssetId(String assetId, OutputStream resultStream) {
        try (final OutputStreamWriter resultWriter = new OutputStreamWriter(resultStream)) {
            printResultsAsCsv(assetId, resultWriter);
        } catch (IOException e) {
            throw new AnalyticResultException("Encountered exception when opening output stream writer", e);
        }
    }

    private void printResultsAsCsv(String assetId, OutputStreamWriter resultWriter) throws IOException {
        final CSVPrinter csvPrinter = new CSVPrinter(resultWriter, DEFAULT);
        jdbcTemplate.query(
                "SELECT extract(EPOCH FROM timestamp) * 1000 AS timestamp, expected_temperature, actual_temperature " +
                        "FROM analytic_result WHERE asset_id = ? ORDER BY timestamp",
                new Object[]{assetId},
                (RowCallbackHandler) resultSet -> printResultRowAsCsv(resultSet, csvPrinter));
    }

    private static void printResultRowAsCsv(ResultSet resultSetAtRow, CSVPrinter resultPrinter) throws SQLException {
        try {
            resultPrinter.printRecord(
                    resultSetAtRow.getLong("timestamp"),
                    resultSetAtRow.getDouble("expected_temperature"),
                    resultSetAtRow.getDouble("actual_temperature"));
        } catch (IOException e) {
            throw new AnalyticResultException("Encountered exception when printing records", e);
        }
    }

}
