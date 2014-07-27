package se.aaslin.developer.finance.server.backend.file.importer;

public enum FileHeader {
	DATE("Datum", 0, false), TRANSACTION("Transaktion", 1, false), CATEGORY("Kategori", 2, true), COST("Belopp", 3, false);
	
    private String header;
    private int colNr;
    private boolean emptyAllowed;

    private FileHeader(String header, int colNr, boolean emptyAllowed) {
            this.header = header;
            this.colNr = colNr;
            this.emptyAllowed = emptyAllowed;
    }

    public String getHeader() {
            return header;
    }

    public int getColNr() {
            return colNr;
    }

    public boolean isEmptyAllowed() {
            return emptyAllowed;
    }

    public static FileHeader fromHeader(String header) throws IllegalArgumentException {
            for (FileHeader o : FileHeader.values()) {
                    if (header.compareToIgnoreCase(o.getHeader()) == 0) {
                            return o;
                    }
            }
            throw new IllegalArgumentException(String.format("[%s] not corresponding to enum value", header));
    }
}
