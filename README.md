# Nt News Android Test Case

. NTNews, kullanıcıların e-posta ve şifre ile giriş yapmasını sağlar, haberleri listeleyip, detaylarına erişmelerine olanak tanır. Ayrıca kullanıcılar, haberleri favorilerine ekleyebilir ve favori haberlerini görebilirler. Uygulama, New York Times Top Stories ve 
Article Search API'larını kullanarak en güncel haberleri sunmaktadır.

#Özellikler:
.Kullanıcı girişi (Email ve Şifre)
.Şifre yenileme fonksiyonu (Şifremi Unuttum)
."Beni Hatırla" özelliği ile otomatik giriş
.Kullanıcı kayıt işlemi
.Haber listesi ve detay ekranı
.Haberleri favorilere ekleme ve favoriler ekranı
.Kategoriye göre haber filtreleme
.Haber arama fonksiyonu
.Gelişmiş API ve log yönetimi

# Kullanım:
.Uygulama açıldığında kullanıcı bir giriş ekranı ile karşılaşır.
.Kullanıcı, e-posta ve şifre bilgileri ile giriş yapabilir veya şifresini unuttuysa "Şifremi 
Unuttum" bağlantısı üzerinden şifresini yenileyebilir.
."Beni Hatırla" özelliği ile kullanıcı bir sonraki girişinde otomatik tanınır.
.Kayıtlı değilse, yeni hesap oluşturabilir ve giriş yapabilir.
.Ana ekranda New York Times'tan çekilen haberler listelenir.
.Kategoriler ekranında, kullanıcı haberleri farklı kategorilere göre filtreleyebilir.
.Kullanıcı haber detayına tıklayarak daha fazla bilgi alabilir ve haberi favorilere ekleyebilir.
.Favorilere eklenen haberler favoriler ekranında gösterilir.
.Kullanıcı aynı zamanda haber arama fonksiyonunu kullanarak makale araması yapabilir.
.Haberin tamamını görüntülemek isterse Read More butonuna tıklayarak cihazdaki varsayılan tarayıcı ile haberin sitesine ulaşabilir.

#Kullanılan Teknolojiler:
 .Kotlin: Android geliştirme dili
 .MVVM Mimarisi: Projenin temel mimarisi
 .Jetpack Compose: Uygulamanın arayüz tasarımı tamamen Jetpack Compose ile yapılmıştır
 .Retrofit: API istekleri için
 .Dagger Hilt: Bağımlılık enjeksiyonu için
 .Firebase Authentication: Kullanıcı girişi ve kaydı için
 .Firebase Storage: Kullanıcı bilgilerini tutmak için
 .New York Times API: Haberler ve makaleler için

#API İyileştirmeleri:
 .Uygulama iki farklı New York Times API'sini kullanmaktadır: Top Stories ve Article Search.
 .API isteklerinde başarı durumları ve hata yönetimi iyileştirilmiştir.
 .Retrofit kullanılarak API istekleri yönetilmiş ve her iki API için buildConfigField kullanılarak API anahtarı güvenli bir şekilde yapılandırılmıştır.
 .SerializedName etiketleri kullanılarak, API modelleri ile JSON veri formatları arasında uyum sağlanmış, veri çekerken karşılaşılabilecek sorunlar önlenmiştir.
 .Proguard konfigürasyonu yapılarak API anahtarlarının gizliliği korunmuştur.

# Yapılan İyileştirmeler:
 .API anahtarlarının buildConfigField ile yönetimi ve proguard-rules.pro dosyası ile log mesajlarının kaldırılması sağlandı.
 .API isteklerinde başarı durumu ve hata yönetimi iyileştirildi.
 .Tüm loglar, Proguard ile üretim (release) sürümünde devre dışı bırakıldı.

# Ekran Görüntüleri:
# 1.Signup ve Login Ekranları:
![Image](https://github.com/user-attachments/assets/43931e39-ed3b-436b-a786-d121d62aae6f)

# 2.Login ve Feed Ekranı:
![Image](https://github.com/user-attachments/assets/dc037190-2c46-443b-af87-173555339cf9)

# 3.Feed ve Read More Ekranı:
![Image](https://github.com/user-attachments/assets/8140ca96-f204-4e2c-a41c-86fc277381b7)

# 4.Kategori,Arama ve Detay Ekranı:
![Image](https://github.com/user-attachments/assets/c125bb09-ba89-4f4f-b40b-81c0329483c4)

# 5.Detay ve Favoriler Ekranı:
![Image](https://github.com/user-attachments/assets/ff7d30f7-9e76-439a-9cc0-df04c63ba788)

# 6.Feed ve Sign Out,Login Ekranları:
![Image](https://github.com/user-attachments/assets/0d8cee5d-7cd6-4392-aa36-4dce71abea20)

# 7.Kontrol Ekranları:
![Image](https://github.com/user-attachments/assets/e094e900-b735-40e8-92d8-a18f2cd66fa5)

# 8.Şifre Sıfırlama Ekranı:
![Image](https://github.com/user-attachments/assets/d3299d8a-5160-455f-8636-bf7b299a0159)


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Nt News Android Test Case

NT+News allows users to log in with email and password, list news articles, and access detailed news content. Users can also add news articles to their favorites and view their favorite news. The app uses the New York Times Top Stories and 
Article Search APIs to provide the latest news.

Features:

 .User login (Email and Password)
 .Password reset function (Forgot Password)
 ."Remember Me" feature for automatic login
 .User registration
 .News list and detail screens
 .Add news articles to favorites and view them in the favorites screen
 .Filter news by category
 .News search function
 .Advanced API and log management

 Usage:
 
 .Upon opening the app, the user is greeted with a login screen.
 .The user can log in with their email and password or reset their password using the "Forgot Password" link if needed.
 .The "Remember Me" feature allows the user to be automatically recognized on subsequent logins.
 .If not registered, the user can create a new account and log in.
 .On the main screen, news fetched from the New York Times is listed.
 .In the categories screen, the user can filter news by various categories.
 .By clicking on a news item, the user can view more details and add the article to their favorites.
 .News added to favorites will be displayed in the favorites screen.
 .Users can also utilize the search function to look for specific articles.
 .To read the full article, the user can click the Read More button, which opens the news site in the device's default browser.

 Screenshots:

 # 1. Signup and Login Screens:
![Image](https://github.com/user-attachments/assets/43931e39-ed3b-436b-a786-d121d62aae6f)

# 2. Login and Feed Screens:
![Image](https://github.com/user-attachments/assets/dc037190-2c46-443b-af87-173555339cf9)

# 3. Feed and Read More Screen:
![Image](https://github.com/user-attachments/assets/8140ca96-f204-4e2c-a41c-86fc277381b7)

# 4. Category, Search, and Detail Screen:
![Image](https://github.com/user-attachments/assets/c125bb09-ba89-4f4f-b40b-81c0329483c4)

# 5. Detail and Favorites Screen:
![Image](https://github.com/user-attachments/assets/ff7d30f7-9e76-439a-9cc0-df04c63ba788)

# 6. Feed and Sign Out, Login Screens:
![Image](https://github.com/user-attachments/assets/0d8cee5d-7cd6-4392-aa36-4dce71abea20)

# 7. Control Screens:
![Image](https://github.com/user-attachments/assets/e094e900-b735-40e8-92d8-a18f2cd66fa5)

# 8. Password Reset Screen:
![Image](https://github.com/user-attachments/assets/d3299d8a-5160-455f-8636-bf7b299a0159)
 


