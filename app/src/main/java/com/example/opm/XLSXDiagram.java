package com.example.opm;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opm.databinding.ActivityXlsxdiagramBinding;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class XLSXDiagram extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_CODE = 1;
    private final HashMap<String, Integer> hashMap = new HashMap<>();
    private ActivityXlsxdiagramBinding binding;
    private String FILE_NAME;
    private String FULL_PATH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXlsxdiagramBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.AddFILE.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            readExcelFile(fileUri);
       }
    }
    private void readExcelFile(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            assert inputStream != null;
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (sheet.getRow(0).getLastCellNum() != 2) {
                System.out.println("Файл должен содержать только два столбца.");
                throw new IllegalArgumentException("Файл должен содержать только два столбца.");
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Row row : sheet) {
                for (Cell cell : row) {
                    stringBuilder.append(getCellValue(cell)).append("\t");
                }
                stringBuilder.append("\n");
            }

            System.out.println(stringBuilder);

            inputStream.close();
        } catch (IOException e) {//
            System.out.println("нЕ норм");
            e.printStackTrace();
        }
    }

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}