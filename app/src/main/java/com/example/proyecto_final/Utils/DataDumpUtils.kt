package com.example.proyecto_final.Utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.proyecto_final.Product.Product
import com.example.proyecto_final.Product.ProductDBHelper
import com.example.proyecto_final.R

class DataDumpUtils(private val context: Context) {

    fun validateAndCreateProductos(context: Context) {
        val productDBHelper = ProductDBHelper(context)
        var productos = productDBHelper.obtenerProductos();
        if(productos.isEmpty()){
            val allProducts = listOf(

                // FARMACIA
                Product(
                        name = "Panadol Niños 160 mg/5 mL Jarabe",
                        sizeProduct = "Frasco 60 mL",
                        description = "Analgésico-antipirético infantil que alivia fiebre y dolores leves de cabeza, garganta, dentición, post-vacunación, etc.",
                        price = 13.70,
                        stock = 50,
                        category = "Farmacia",
                        image = R.drawable.p1_panadol
                ),

                Product(
                    name = "Leche Magnesia Phillip 400Mg/5Ml Suspensión Oral",
                    sizeProduct = "FRASCO 120 ML",
                    description = "Leche Magnesia Phillip 400Mg/5Ml Suspensión Oral",
                    price = 7.90,
                    stock = 50,
                    category = "Farmacia",
                    image = R.drawable.p2_magenesol
                ),

                Product(
                        name = "Ibuprofeno 400 mg Tableta Recubierta",
                        sizeProduct = "Caja 100 tabletas",
                        description = "AINE que reduce inflamación, alivia el dolor y baja la fiebre.",
                        price = 14.00,
                        stock = 100,
                        category = "Farmacia",
                        image = R.drawable.p3_ibupofreno
                ),

                Product(
                    name = "Paracetamol 500 mg Tableta",
                    sizeProduct = "Caja 100 tabletas",
                    description = "Analgésico-antipirético para el alivio de diversos dolores y la fiebre.",
                    price = 10.00,
                    stock = 120,
                    category = "Farmacia",
                    image = R.drawable.p4_paracetamol
                ),

                Product(
                    name = "Panadol 500 mg Tableta",
                    sizeProduct = "Sobre x 2 tabletas",
                    description = "Paracetamol 500 mg de liberación rápida; no irrita el estómago.",
                    price = 1.10,
                    stock = 200,
                    category = "Farmacia",
                    image = R.drawable.p5_panadol
                ),

                Product(
                    name = "Paracetamol 160 mg/5 mL Jarabe",
                    sizeProduct = "Frasco 60 mL",
                    description = "Jarabe pediátrico sabor fresa-cereza indicado a partir de 2 años.",
                    price = 4.80,
                    stock = 60,
                    category = "Farmacia",
                    image = R.drawable.p6_paracetamoljarabe
                ),

                Product(
                    name = "Dolocheck Plus 400 mg Cápsulas Blandas",
                    sizeProduct = "Blíster 10 cápsulas",
                    description = "Ibuprofeno en cápsula blanda para alivio rápido de dolor e inflamación.",
                    price = 12.00,
                    stock = 80,
                    category = "Farmacia",
                    image = R.drawable.p7_dolocheck
                ),

                // SALUD
                Product(
                    name = "Gomitas Nutricionales Arándanos 60 u",
                    sizeProduct = "Frasco 60 gomitas",
                    description = "Gomitas con antioxidantes de arándano para salud visual y bienestar general.",
                    price = 48.90,
                    stock = 50,
                    category = "Salud",
                    image = R.drawable.p8_gomas
                ),

                Product(
                    name = "Miositol Men Polvo Oral 14 sobres",
                    sizeProduct = "Caja 14 sobres",
                    description = "Polvo oral con mio-inositol, apoyo para la salud hormonal masculina.",
                    price = 106.90,
                    stock = 30,
                    category = "Salud",
                    image = R.drawable.p9_miositol
                ),

                Product(
                    name = "Ensure Advance Vainilla 220 mL",
                    sizeProduct = "Pack 15 unidades",
                    description = "Bebida nutricional completa sabor vainilla, ideal para recuperación o soporte alimenticio.",
                    price = 196.50,
                    stock = 20,
                    category = "Salud",
                    image = R.drawable.p10_ensure
                ),

                Product(
                    name = "Calcitriol 0.25 µg Cápsula",
                    sizeProduct = "Caja 100 cápsulas",
                    description = "Suplemento de Calcitriol para soporte de metabolismo óseo.",
                    price = 218.00,
                    stock = 15,
                    category = "Salud",
                    image = R.drawable.p11_calcitriol
                ),

                Product(
                    name = "Prevencel Cápsula Blanda 15 u",
                    sizeProduct = "Blíster 15 cápsulas",
                    description = "Suplemento con vitaminas y minerales para refuerzo inmunológico.",
                    price = 35.30,
                    stock = 40,
                    category = "Salud",
                    image = R.drawable.p12_prevencel
                ),

                Product(
                    name = "Dynamogén Solución Oral",
                    sizeProduct = "Caja x 10 ampollas",
                    description = "Solución energizante con vitaminas del complejo B para recuperación física.",
                    price = 50.00,
                    stock = 25,
                    category = "Salud",
                    image = R.drawable.p13_dynamogen
                ),

                //CUIDADO CABELLO
                Product(
                    name = "Aceite Intensivo Kativa Argán",
                    sizeProduct = "Pote 120 mL",
                    description = "Aceite con argán que otorga brillo natural y recupera la vitalidad del cabello. Libre de sal, sulfatos y parabenos.",
                    price = 39.90,
                    stock = 40,
                    category = "Cuidado del cabello",
                    image = R.drawable.p14_aceite
                ),

                Product(
                    name = "Ampollas Capilares Placenta Life Hidro Nutritivo",
                    sizeProduct = "3 unidades",
                    description = "Ampollas altamente hidratantes y nutritivas con resultados visibles desde la primera aplicación.",
                    price = 24.50,
                    stock = 30,
                    category = "Cuidado del cabello",
                    image = R.drawable.p15_ampollas
                ),

                Product(
                    name = "Shampoo Capilatis Anticaída Cabello Seco",
                    sizeProduct = "Frasco 410 mL",
                    description = "Shampoo anticaída para cabello seco que penetra la cutícula e hidrata formando un film protector.",
                    price = 28.90,
                    stock = 55,
                    category = "Cuidado del cabello",
                    image = R.drawable.p16_ampollas
                ),

                Product(
                    name = "Mascarilla Fructis Hair Food Coco 350 mL",
                    sizeProduct = "Tarro 350 mL",
                    description = "Mascarilla reparadora con aceite de coco, formulada para reparar intensamente el cabello reseco y deshidratado.",
                    price = 32.90,
                    stock = 45,
                    category = "Cuidado del cabello",
                    image = R.drawable.p17_mascarilla
                ),

            )
            for (product in allProducts) {
                productDBHelper.insertarProducto(product)
            }
        }
    }

}