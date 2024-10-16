package io.github.vkaze.crayon;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PsiErrorElementUtil;
import io.github.vkaze.crayon.services.MyProjectService;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class MyPluginTest extends BasePlatformTestCase {
    public void testXMLFile() {
        PsiFile psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>");
        XmlFile xmlFile = assertInstanceOf(psiFile, XmlFile.class);
        assertFalse(PsiErrorElementUtil.hasErrors(getProject(), xmlFile.getVirtualFile()));
        XmlTag rootTag = xmlFile.getRootTag();
        assertNotNull(rootTag);
        assertEquals("foo", rootTag.getName());
        assertEquals("bar", rootTag.getValue().getText());
    }

    public void testRename() {
        myFixture.testRename("foo.xml", "foo_after.xml", "a2");
    }

    public void testProjectService() {
        MyProjectService projectService = getProject().getService(MyProjectService.class);
        assertNotSame(projectService.getRandomNumber(), projectService.getRandomNumber());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/rename";
    }
}
