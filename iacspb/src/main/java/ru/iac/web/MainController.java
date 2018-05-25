package ru.iac.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.iac.domain.ListTable;
import ru.iac.domain.PathTable;
import ru.iac.service.ListService;
import ru.iac.service.PathService;
import ru.iac.utils.ReadDirAndFiles;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Controller
public class MainController {
    private static final Logger LOG = Logger.getLogger(MainController.class);
    private final PathService pathService;
    private final ListService listService;
    private final ReadDirAndFiles disk;

    @Autowired
    public MainController(PathService pathService, ListService listService) {
        LOG.info("Загрузка контроллера 'MainController'.");
        this.pathService = pathService;
        this.listService = listService;
        this.disk = new ReadDirAndFiles();
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showMainPage(ModelMap model) {
        LOG.info("Загрузка данных на главную страницу.");
        model.addAttribute("paths", pathService.getAll());
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam("path") String path, ModelMap model) {
        LOG.info("Запрос на добавление новой директории.");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (disk.isDir(path)) {
            LOG.info("Директория найдена.");
            PathTable pathTable = new PathTable();
            pathTable.setPath(path);
            pathTable.setTime(timestamp);
            pathService.add(pathTable);
            for (ListTable item: disk.getAll(path, pathTable)) {
                listService.add(item);
            }
            return "redirect:main";
        } else {
            LOG.info("Директория не найдена.");
            model.addAttribute("error", path);
            model.addAttribute("paths", pathService.getAll());
        }
        return "main";
    }

    @PostMapping("/view")
    public String view(@RequestParam("path") String id, ModelMap model) {
        LOG.info("Загрузка вложенных директорий и файлов.");
        PathTable pathTable = pathService.findById(Integer.parseInt(id));
        List<ListTable> listTables = listService.getById(pathTable);
        Collections.sort(listTables);
        model.addAttribute("path", pathTable.getPath());
        model.addAttribute("list", listTables);
        return "view";
    }
}
