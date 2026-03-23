package com.mach.utils

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import com.mach.utils.api.MessagesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TestPluginClass {
    private lateinit var server: ServerMock

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    @DisplayName("Main enables and initializes FrameworkContext")
    fun enablesAndInitializesContext() {
        val plugin = MockBukkit.load(Main::class.java)

        assertTrue(plugin.isEnabled, "Plugin should be enabled after load")

        val ctxField = Main::class.java.getDeclaredField("ctx")
        ctxField.isAccessible = true
        assertNotNull(ctxField.get(plugin), "FrameworkContext should be assigned during onEnable")
    }

    @Test
    @DisplayName("Main initializes MessagesApi during onEnable")
    fun initializesMessagesApi() {
        MockBukkit.load(Main::class.java)

        assertDoesNotThrow("MessagesApi should be initialized in onEnable") {
            MessagesApi.get("tests.main.initialized", "ok")
        }
    }

    @Test
    @DisplayName("Main disables without throwing after plugin enable")
    fun disablesSafelyAfterEnable() {
        val plugin = MockBukkit.load(Main::class.java)

        assertDoesNotThrow("onDisable should be safe after successful onEnable") {
            plugin.onDisable()
        }
    }
}