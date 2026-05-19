const express = require("express");
const cors = require("cors");
const { MongoClient } = require("mongodb");

const app = express();
const PORT = 3000;

const mongoUrl = "mongodb://localhost:27018";
const dbName = "gigtrackDB";

app.use(cors());
app.use(express.json());

let db;

async function connectDB() {
    try {
        const client = new MongoClient(mongoUrl);
        await client.connect();
        db = client.db(dbName);
        console.log("Connectat correctament a MongoDB");
    } catch (error) {
        console.error("Error connectant a MongoDB:", error);
    }
}

app.get("/", (req, res) => {
    res.json({
        missatge: "API REST GigTrack funcionant"
    });
});

app.get("/clients", async (req, res) => {
    try {
        const clients = await db.collection("clients").find().toArray();
        res.json(clients);
    } catch (error) {
        res.status(500).json({ error: "Error obtenint clients" });
    }
});

app.get("/materials", async (req, res) => {
    try {
        const materials = await db.collection("materials").find().toArray();
        res.json(materials);
    } catch (error) {
        res.status(500).json({ error: "Error obtenint materials" });
    }
});

app.get("/esdeveniments", async (req, res) => {
    try {
        const esdeveniments = await db.collection("esdeveniments").find().toArray();
        res.json(esdeveniments);
    } catch (error) {
        res.status(500).json({ error: "Error obtenint esdeveniments" });
    }
});

connectDB().then(() => {
    app.listen(PORT, "0.0.0.0", () => {
        console.log(`Servidor API GigTrack en funcionament a http://localhost:${PORT}`);
    });
});