const express = require('express');
const bcrypt = require('bcryptjs');
const app = express();
app.use(express.json());

const usuarios = [
    {
        id: 1,
        usuario: 'usuario1',
        contrasena: '$2a$10$P.VN9Rl14U4HfgbdgFuUP.c0kY.V3w5MwRzwjswOYUo0.M6ePqNbu',  // Contraseña cifrada (ejemplo)
    }
];

app.post('/api/login', (req, res) => {
    const { usuario, contrasena } = req.body;

    // Buscar el usuario en la base de datos (en este caso un array de ejemplo)
    const usuarioDB = usuarios.find(u => u.usuario === usuario);
    
    if (usuarioDB) {
        // Verificar la contraseña
        bcrypt.compare(contrasena, usuarioDB.contrasena, (err, result) => {
            if (result) {
                // Si las contraseñas coinciden
                res.json({ success: true, message: 'Inicio de sesión exitoso' });
            } else {
                res.json({ success: false, message: 'Contraseña incorrecta' });
            }
        });
    } else {
        res.json({ success: false, message: 'Usuario no encontrado' });
    }
});

app.listen(3000, () => {
    console.log('Servidor escuchando en el puerto 3000');
});
