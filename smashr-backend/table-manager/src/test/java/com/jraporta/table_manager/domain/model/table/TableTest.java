package com.jraporta.table_manager.domain.model.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    private Table testTable;
    private final String testId = "testId";
    private final String testName = "testName";
    private final String testDescription = "testDescription";

    @BeforeEach
    void init() {
        testTable = new Table(testId, testName, testDescription);
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GettersSettersTest {

        @Test
        void getId_shouldReturnCorrectId() {
            assertEquals(testId, testTable.getId());
        }

        @Test
        void getName_shouldReturnCorrectName() {
            assertEquals(testName, testTable.getName());
        }

        @Test
        void getDescription_shouldReturnCorrectDescription() {
            assertEquals(testDescription, testTable.getDescription());
        }

        @Test
        void setId_shouldUpdateId() {
            String newId = "newId";
            testTable.setId(newId);
            assertEquals(newId, testTable.getId());
        }

        @Test
        void setName_shouldUpdateName() {
            String newName = "newName";
            testTable.setName(newName);
            assertEquals(newName, testTable.getName());
        }

        @Test
        void setDescription_shouldUpdateDescription() {
            String newDescription = "newDescription";
            testTable.setDescription(newDescription);
            assertEquals(newDescription, testTable.getDescription());
        }
    }

    @Nested
    @DisplayName("Equality Tests")
    class EqualsTest {

        @Test
        void testEquals_shenSameIdAndNameAndDescription_shouldReturnTrue() {
            Table table1 = new Table("id", "name", "description");
            Table table2 = new Table("id", "name", "description");
            assertEquals(table1, table2);
        }

        @Test
        void testEquals_whenDifferentID_shouldReturnFalse() {
            Table table1 = new Table("id1", "name", "description");
            Table table2 = new Table("id2", "name", "description");
            assertNotEquals(table1, table2);
        }

        @Test
        void testEquals_whenDifferentName_shouldReturnFalse() {
            Table table1 = new Table("id", "name1", "description");
            Table table2 = new Table("id", "name2", "description");
            assertNotEquals(table1, table2);
        }

        @Test
        void testEquals_whenDifferentDescription_shouldReturnFalse() {
            Table table1 = new Table("id", "name", "description1");
            Table table2 = new Table("id", "name", "description2");
            assertNotEquals(table1, table2);
        }

        @Test
        @DisplayName("Table should not be equal to null")
        void equals_withNull_shouldReturnFalse() {
            assertNotEquals(null, testTable);
        }

        @Test
        @DisplayName("Table should be equal to itself")
        void equals_withItself_shouldReturnTrue() {
            assertTrue(testTable.equals(testTable));
        }
    }
}