<div class="login">
    <h1><a href="index">GamingPlatform</a></h1>
    <div class="login-bottom">
        <h2>Signup</h2>
        <div class="col-md-6">
            <div class="login-mail">
                <input type="text" placeholder="Name" name="name" required="">
                <i class="fa fa-address-card-o"></i>
            </div>
            <div class="login-mail">
                <input type="text" placeholder="Surname" name="surname" required="">
                <i class="fa fa-address-card-o"></i>
            </div>
            <div class="login-mail">
                <input type="text" placeholder="Username" name="username" required="">
                <i class="fa fa-user-circle"></i>
            </div>
            <div class="login-mail">
                <input type="email" placeholder="Email" name="email" required="">
                <i class="fa fa-envelope"></i>
            </div>
            <div class="login-mail">
                <input type="password" placeholder="Password" oninput="document.getElementById('password2').pattern = escapeRegExp(this.value)" name="password" required="">
                <i class="fa fa-lock"></i>
            </div>
            <div class="login-mail">
                <input type="password" id="password2" placeholder="Repeat password" required="">
                <i class="fa fa-lock"></i>
            </div>
            <div class="login-mail">
                <input type="file" placeholder="Avatar (.jpg only), leave blank to use the default one" name="avatar">
                <i class="fa fa-file-photo-o"></i>
            </div>

        </div>
        <div class="col-md-6 login-do">
            <label class="hvr-shutter-in-horizontal login-sub">
                <input type="submit" value="Submit">
            </label>
            <p class="personalizedP">Already registered?</p>
            <a href="login" class="hvr-shutter-in-horizontal">Login</a>
        </div>
        <div class="clearfix"> </div>
    </div>
</div>