<div class="login">
    <h1><a href="index">GamingPlatform</a></h1>
    <div class="login-bottom">
        <h2>Signup</h2>
        <form method="POST" enctype="multipart/form-data">
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
            <h6 style="color:silver; margin-bottom:10px; margin-top:-10px;">Upload an avatar, if you do not choose an avatar the default one will be used (only .jpg and .png allowed)</h6>
            <div class="login-mail">
                <input type="file" name="avatar">
            </div>


        </div>
        <div class="col-md-6 login-do">
            <label class="hvr-shutter-in-horizontal login-sub">
                <input type="submit" value="Submit">
            </label>
            <p class="personalizedP">Already registered?</p>
            <a href="login" class="hvr-shutter-in-horizontal">Login</a>
        </div>
        </form>

        <div class="clearfix"> </div>
    </div>
</div>