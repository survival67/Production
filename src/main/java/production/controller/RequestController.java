package production.controller;

import java.util.Map;

public class RequestController {
	public String action; // insert, update, delete, select
    public String entity; // product, component, detail
    public Map<String, Object> data; // Поля: name, serialNumber, id
}
