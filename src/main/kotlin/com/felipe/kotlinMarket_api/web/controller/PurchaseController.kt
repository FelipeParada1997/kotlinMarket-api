package com.felipe.kotlinMarket_api.web.controller

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.service.PurchaseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@Tag(name = "Compras", description = "Operaciones relacionadas con compras")
@RestController
@RequestMapping("/purchases")
class PurchaseController(
    @Autowired
    private val purchaseService: PurchaseService
) {
    private val logger = Logger.getLogger(PurchaseController::class.java.name)

    @Operation(summary = "Obtener todas las compras", description = "Retorna una lista de todas las compras registradas.")
    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<Purchase>> {
        return ResponseEntity(purchaseService.getAll(), HttpStatus.OK)
    }

    @Operation(summary = "Obtener compras por cliente", description = "Retorna las compras realizadas por un cliente específico.")
    @GetMapping("/client/{clientId}")
    fun getByClient(@PathVariable("clientId") clientId: String): ResponseEntity<List<Purchase>> {
        return purchaseService.getByClient(clientId)
            .map { purchases -> ResponseEntity(purchases, HttpStatus.OK) }
            .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @Operation(summary = "Guardar compra", description = "Guarda una nueva compra en la base de datos.")
    @PostMapping("/save")
    fun save(@RequestBody purchase: Purchase): ResponseEntity<Any> {
        return try {
            // Validaciones previas
            if (purchase.items.isEmpty()) {
                return ResponseEntity
                    .badRequest()
                    .body(mapOf("error" to "La compra debe tener al menos un producto"))
            }

            val savedPurchase = purchaseService.save(purchase)
            ResponseEntity(savedPurchase, HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            logger.warning("Error de validación: ${e.message}")
            ResponseEntity
                .badRequest()
                .body(mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.severe("Error al guardar la compra: ${e.message}")
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Error al procesar la compra", "message" to e.message))
        }
    }
}

