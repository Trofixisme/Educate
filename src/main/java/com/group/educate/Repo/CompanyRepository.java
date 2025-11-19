package com.group.educate.Repo;

import com.group.educate.Model.User.Company.Company;
import com.group.educate.Model.User.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private final String path = "data/companies.txt";

    public CompanyRepository() {
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
    }

    private String formatCompany(Company company) {
        return company.toString();
    }

    public void save(Company company) {
        String line = formatCompany(company);
        writeToFile(path, line, true);
    }

    private void writeToFile(String path, String line, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readFromFile(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
        }
        return lines;
    }
    private Company parseCompany(String line) {
        String[] parts = line.split("\\|");
        int companyId=Integer.parseInt(parts[0]);
        String industry = parts[1];
        String name = parts[2];
        String websiteURL = parts[3];
        String role = parts[4];

        return new Company(parts[1],parts[2],parts[3],UserRole.COMPANY);
    }
}
// i didnt add find methods yet am aware


