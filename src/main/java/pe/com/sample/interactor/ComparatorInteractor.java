package pe.com.sample.interactor;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import pe.com.sample.bean.entity.Entidad;
import pe.com.sample.presenter.ComparatorPresenter;

import java.util.concurrent.TimeUnit;

public class ComparatorInteractor {

    private ComparatorPresenter comparatorPresenter;

    public ComparatorInteractor(ComparatorPresenter comparatorPresenter) {
        this.comparatorPresenter = comparatorPresenter;
    }

    public void generateExcel() {
        Observable.create(subscriber -> {
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));
            subscriber.onNext(new Entidad("123","123"));

            subscriber.onComplete(); })
                .subscribeOn(Schedulers.newThread())
                .observeOn(JavaFxScheduler.platform())
                .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), (item, interval) -> item)
                .subscribe(
                        onResult -> comparatorPresenter.progressBarPlus(),
                        onError -> comparatorPresenter.generateError(),
                        () -> comparatorPresenter.generateSuccess()
        );
    }

    /*
    @Test
    public void insertListProducto() throws FileNotFoundException {
        System.out.println("Me ejecute" + productoService);
        List<ImportacionMedicamentoTable> listImp = new ArrayList<>();
        loadObservableMedicamento(new FileInputStream(new File("/home/clario/Documentos/Productos.xls"))).subscribe(listImp::add);
        List<Producto> lista = new ArrayList<>();
        loadObservableProducto(listImp).subscribe(lista::add);
        productoService.insertListProductoDigemid(lista);
    }

    private Observable<ImportacionMedicamentoTable> loadObservableMedicamento(InputStream inputStream) {
        return Observable.create(subscriber -> {
            try (HSSFWorkbook workbook = new HSSFWorkbook(inputStream)) {
                HSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                Stream<Row> stream = StreamSupport
                        .stream(Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED), false);
                stream.forEach(row -> {
                    if (row.getRowNum() > 2) {
                        ImportacionMedicamentoTable importacionMedicamentoTable = new ImportacionMedicamentoTable();
                        StreamSupport
                                .stream(Spliterators.spliteratorUnknownSize(row.cellIterator(), Spliterator.ORDERED), false)
                                .forEach(cell -> convertObject(cell, importacionMedicamentoTable));
                        if (importacionMedicamentoTable.getNombre() != null) {
                            subscriber.onNext(importacionMedicamentoTable);
                        }
                    }
                });
                subscriber.onComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void convertObject(Cell cell, ImportacionMedicamentoTable importacionMedicamentoTable) {
        if (cell.getCellTypeEnum() == CellType.BLANK)
            return;
        switch (cell.getColumnIndex()) {
            case 0:
                importacionMedicamentoTable.setCodigoDigemid(String.valueOf((int) cell.getNumericCellValue()));
                break;
            case 1:
                if (cell.getCellTypeEnum().equals(CellType.STRING))
                    importacionMedicamentoTable.setNombre(cell.getRichStringCellValue().getString());
                else if (cell.getCellTypeEnum().equals(CellType.NUMERIC))
                    importacionMedicamentoTable.setNombre(String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
                break;
            case 2:
                if (cell.getCellTypeEnum().equals(CellType.STRING))
                    importacionMedicamentoTable.setConcentracion(cell.getRichStringCellValue().getString());
                else if (cell.getCellTypeEnum().equals(CellType.NUMERIC))
                    importacionMedicamentoTable.setConcentracion(String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
                break;
            case 3:
                importacionMedicamentoTable.setFormaFarm(cell.getRichStringCellValue().getString());
                break;
            case 4:
                importacionMedicamentoTable.setForma(cell.getRichStringCellValue().getString());
                break;
            case 5:
                importacionMedicamentoTable.setPresentacion(cell.getRichStringCellValue().getString());
                break;
            case 6:
                importacionMedicamentoTable.setUndCaja((int) cell.getNumericCellValue());
                break;
            case 9:
                importacionMedicamentoTable.setLaboratorio(cell.getRichStringCellValue().getString());
                break;
        }
    }

    private Observable<Producto> loadObservableProducto(List<ImportacionMedicamentoTable> listaImportacionMedicamento) {
        return Observable.create(subscriber -> {
            List<ProductoUnidadCaja> listaProductoUnidadCaja = listaImportacionMedicamento.stream().map(this::convertImportToMedicamento).collect(Collectors.toList());

            List<Producto> listaProducto = Observable.fromIterable(listaProductoUnidadCaja).map(ProductoUnidadCaja::getProducto)
                    .distinct(Producto::getDescripcion).toList().blockingGet();

            listaProducto.parallelStream().forEach(producto -> producto.setListProductoUnidadCaja(listaProductoUnidadCaja.parallelStream()
                    .filter(prodUndCaja -> prodUndCaja.getProducto().getDescripcion().equals(producto.getDescripcion()))
                    .collect(Collectors.toList())));

            listaProducto.forEach(producto -> {
                producto.setListProductoUnidadCaja(Observable.fromIterable(producto.getListProductoUnidadCaja()).distinct(ProductoUnidadCaja::getUndCaja).toList().blockingGet());
                producto.setDescripcion(null);
                producto.setUserIdReg(0);
                subscriber.onNext(producto);
            });
            subscriber.onComplete();
        });
    }

    private ProductoUnidadCaja convertImportToMedicamento(ImportacionMedicamentoTable importacion) {

        Medicamento medicamento = new Medicamento();
        medicamento.setNombreComercial(importacion.getNombre().trim().replaceAll("\u00A0", "").trim() + (importacion.getConcentracion() != null ? " " + importacion.getConcentracion().trim().replaceAll("\u00A0", "").trim() : "") + " " + importacion.getForma().trim().replaceAll("\u00A0", "").trim());
        medicamento.setTipoProducto("M");
        medicamento.setUnidadMedida(new UnidadMedida(1));
        Presentacion presentacion = new Presentacion(importacion.getPresentacion().trim().replaceAll("\u00A0", "").trim());
        Farmaco farmaco = new Farmaco(importacion.getNombre().trim().replaceAll("\u00A0", "").trim());
        Laboratorio laboratorio = new Laboratorio(importacion.getLaboratorio().trim().replaceAll("\u00A0", "").trim());
        FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica();
        formaFarmaceutica.setNombre(importacion.getFormaFarm().trim().replaceAll("\u00A0", "").trim());
        formaFarmaceutica.setForma(new Forma(importacion.getForma().trim().replaceAll("\u00A0", "").trim()));

        medicamento.setFormaFarmaceutica(formaFarmaceutica);
        medicamento.setFarmaco(farmaco);
        medicamento.setPresentacion(presentacion);
        medicamento.setConcentracion(importacion.getConcentracion());
        medicamento.setLaboratorio(laboratorio);

        // Esto solo es usado para identificar los productos que son iguales, sin incluir la undCaja
        medicamento.setDescripcion(importacion.getPresentacion().trim().replaceAll("\u00A0", "").trim().toUpperCase() + importacion.getNombre().trim().replaceAll("\u00A0", "").trim().toUpperCase() +
                importacion.getLaboratorio().trim().replaceAll("\u00A0", "").trim().toUpperCase() + importacion.getFormaFarm().trim().replaceAll("\u00A0", "").trim().toUpperCase() + importacion.getForma().trim().replaceAll("\u00A0", "").trim().toUpperCase()
                + (Objects.isNull(importacion.getConcentracion()) ? "" : importacion.getConcentracion().trim().replaceAll("\u00A0", "").trim().toUpperCase()));

        ProductoUnidadCaja productoUnidadCaja = new ProductoUnidadCaja();
        productoUnidadCaja.setProducto(medicamento);
        productoUnidadCaja.setUndCaja(importacion.getUndCaja());
        ProductoCodigoDigemid productoCodigoDigemid = new ProductoCodigoDigemid(Integer.valueOf(importacion.getCodigoDigemid()));
        productoCodigoDigemid.setUserIdReg(0);
        productoUnidadCaja.setProductoCodigoDigemid(productoCodigoDigemid);

        return productoUnidadCaja;
    }
     */


}
